package com.bash.hotel_booking_platfom.controller;

import com.bash.hotel_booking_platfom.dto.LoginRequest;
import com.bash.hotel_booking_platfom.dto.RegisterRequest;
import com.bash.hotel_booking_platfom.dto.UserDto;
import com.bash.hotel_booking_platfom.model.ResponseModel;
import com.bash.hotel_booking_platfom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

}
