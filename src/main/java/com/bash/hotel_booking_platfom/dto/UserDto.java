package com.bash.hotel_booking_platfom.dto;

import com.bash.hotel_booking_platfom.model.User;

import java.util.UUID;

public record UserDto(UUID id, String name, String email, int age) {
    public UserDto(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getAge());
    }
}
