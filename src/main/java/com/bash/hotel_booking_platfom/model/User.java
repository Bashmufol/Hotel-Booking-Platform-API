package com.bash.hotel_booking_platfom.model;


import lombok.Data;

import java.util.UUID;
@Data
public class User {
    private UUID id;
    private String name;
    private String email;
    private int age;

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
