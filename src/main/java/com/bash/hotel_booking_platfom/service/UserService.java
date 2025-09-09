package com.bash.hotel_booking_platfom.service;

import com.bash.hotel_booking_platfom.Repository.UserRepository;
import com.bash.hotel_booking_platfom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user){
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setAge(user.getAge());
        return userRepository.save(newUser);
    }

    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public String deleteUser(UUID id) {
        userRepository.deleteById(id);
        return "User deleted";
    }
    public User updateUser(UUID id, User user) {
        if(userRepository.findById(id).isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "All users deleted";
    }
}
