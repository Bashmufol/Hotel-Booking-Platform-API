package com.bash.hotel_booking_platfom.service;

import com.bash.hotel_booking_platfom.Repository.UserRepository;
import com.bash.hotel_booking_platfom.dto.LoginRequest;
import com.bash.hotel_booking_platfom.dto.RegisterRequest;
import com.bash.hotel_booking_platfom.dto.UserDto;
import com.bash.hotel_booking_platfom.exception.InvalidCredentialsException;
import com.bash.hotel_booking_platfom.exception.ResourceNotFoundException;
import com.bash.hotel_booking_platfom.model.ResponseModel;
import com.bash.hotel_booking_platfom.model.Role;
import com.bash.hotel_booking_platfom.model.User;
import com.bash.hotel_booking_platfom.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
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
            put = { @CachePut(value = "USER_CACHE", key = "#result.id") },      // cache the newly created user by ID
            evict = { @CacheEvict(value = "USER_CACHE", key = "'all'") }        // clear the "all users" cache so it's rebuilt
    )
    public ResponseModel<UserDto> createUser(RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.email()).isPresent()){
            throw new IllegalArgumentException("User already exists");
        }
        User newUser = new User();
        newUser.setName(registerRequest.name());
        newUser.setAge(registerRequest.age());
        newUser.setEmail(registerRequest.email());
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        newUser.setRole(Role.USER);
        userRepository.save(newUser);
        return new ResponseModel<>( "User Successfully Created", HttpStatus.CREATED.value(), new UserDto(newUser));
    }

    public ResponseModel<String> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            String token = jwtService.generateAccessToken(principal);
            return new ResponseModel<>( "User Successfully Created", HttpStatus.OK.value(), token);
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    @Cacheable(value = "USER_CACHE", key = "#id")       // caches individual users by ID
    public ResponseModel<UserDto> findUserById(UUID id) {
        long start = System.nanoTime();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        long end = System.nanoTime();
        log.info("DB call took {} ms", (end - start) / 1_000_000);
        return new ResponseModel<>("User details fetched", HttpStatus.OK.value(), new UserDto(user));
    }

    @Cacheable(value = "USER_CACHE", key = "'all'")     // caches the list of all users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Caching(evict = {
            @CacheEvict(value = "USER_CACHE", key = "#id"),     // remove this user from cache
            @CacheEvict(value = "USER_CACHE", key = "'all'")    // also clear the "all users" cache
    })
    public String deleteUser(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "User deleted";
        }
        return "User not found";
    }

    @Caching(
            put = { @CachePut(value = "USER_CACHE", key = "#result.id") },  // update individual user cache
            evict = { @CacheEvict(value = "USER_CACHE", key = "'all'") }    // clear the "all users" cache
    )
    public ResponseModel<UserDto> updateUser(UUID id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        existingUser.setName(updatedUser.getName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());
        return new ResponseModel<>("User details updated", HttpStatus.ACCEPTED.value(), new UserDto(existingUser));
    }

    @CacheEvict(value = "USER_CACHE", allEntries = true)    // clears *all* entries in the user cache
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "All users deleted";
    }
}
