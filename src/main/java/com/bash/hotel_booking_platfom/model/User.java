package com.bash.hotel_booking_platfom.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;
@Data
@Table(name = "users")
@Entity
public class User {
    @Id
    private UUID id;
    private String name;
    private String email;
    private int age;

}
