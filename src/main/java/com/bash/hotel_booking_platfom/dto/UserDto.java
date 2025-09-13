package com.bash.hotel_booking_platfom.dto;

import com.bash.hotel_booking_platfom.model.User;

public record UserDto(String name, String email, int age) {
    public UserDto(User user) {
        this(user.getName(), user.getEmail(), user.getAge());
    }
}
