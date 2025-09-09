package com.bash.hotel_booking_platfom.controller;

import com.bash.hotel_booking_platfom.Repository.UserRepository;
import com.bash.hotel_booking_platfom.model.User;
import com.bash.hotel_booking_platfom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public User createUser(User user) {
        return userService.createUser(user);
    }
    @PostMapping("/update/{id}")
    public User updateUser(@PathVariable UUID id, User user) {
        return userService.updateUser(id, user);
    }

}
