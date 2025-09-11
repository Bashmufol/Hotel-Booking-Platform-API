package com.bash.hotel_booking_platfom.service;

import com.bash.hotel_booking_platfom.Repository.UserRepository;
import com.bash.hotel_booking_platfom.dto.LoginRequest;
import com.bash.hotel_booking_platfom.dto.RegisterRequest;
import com.bash.hotel_booking_platfom.model.Role;
import com.bash.hotel_booking_platfom.model.User;
import com.bash.hotel_booking_platfom.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Caching(
            put = { @CachePut(value = "USER_CACHE", key = "#result.getId()") },
            evict = { @CacheEvict(value = "USER_CACHE", key = "'all'") }
    )
    public User createUser(RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.email()).isPresent()){
            throw new IllegalArgumentException("User already exists");
        }
        User newUser = new User();
        newUser.setName(registerRequest.name());
        newUser.setAge(registerRequest.age());
        newUser.setEmail(registerRequest.email());
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        newUser.setRole(Role.USER);
        return userRepository.save(newUser);
    }

    public String login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            return jwtService.generateAccessToken(principal);
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    @Cacheable(value = "USER_CACHE", key = "#id")
    public User findUserById(UUID id) {
        long start = System.nanoTime();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        long end = System.nanoTime();
        log.info("DB call took {} ms", (end - start) / 1_000_000);

        return user;
    }

    @Cacheable(value = "USER_CACHE", key = "'all'")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Caching(evict = {
            @CacheEvict(value = "USER_CACHE", key = "#id"),
            @CacheEvict(value = "USER_CACHE", key = "'all'")
    })
    public String deleteUser(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "User deleted";
        }
        return "User not found";
    }

    @Caching(
            put = { @CachePut(value = "USER_CACHE", key = "#result.getId()") },
            evict = { @CacheEvict(value = "USER_CACHE", key = "'all'") }
    )
    public User updateUser(UUID id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        existingUser.setName(updatedUser.getName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }

    @CacheEvict(value = "USER_CACHE", allEntries = true)
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "All users deleted";
    }
}
