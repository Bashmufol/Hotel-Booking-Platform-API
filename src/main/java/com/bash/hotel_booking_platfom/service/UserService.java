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
        return userRepository.save(user);
    }

    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public String deleteUser(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "User deleted";
        }
        return "User not found";
    }
    public User updateUser(UUID id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        existingUser.setName(updatedUser.getName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "All users deleted";
    }
}
