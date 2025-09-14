package com.bash.hotel_booking_platfom.controller;


import com.bash.hotel_booking_platfom.dto.LoginRequest;
import com.bash.hotel_booking_platfom.dto.RegisterRequest;
import com.bash.hotel_booking_platfom.dto.UserDto;
import com.bash.hotel_booking_platfom.model.ResponseModel;
import com.bash.hotel_booking_platfom.model.User;
import com.bash.hotel_booking_platfom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseModel<UserDto> createUser(@RequestBody RegisterRequest registerRequest) {
        return userService.createUser(registerRequest);
    }

    @PostMapping("/login")
    public ResponseModel<String> login(@RequestBody LoginRequest loginRequest) {
       return userService.login(loginRequest);
    }
    @PostMapping("/update/{id}")
    public ResponseModel<UserDto> updateUser(@PathVariable UUID id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/get-user/{id}")
    public ResponseModel<UserDto> findById(@PathVariable UUID id) {

        return userService.findUserById(id);
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/delete-all")
    public String deleteAllUsers() {
        return userService.deleteAllUsers();
    }

}
