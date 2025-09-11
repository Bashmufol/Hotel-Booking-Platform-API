package com.bash.hotel_booking_platfom.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Data
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String name;
    @Column(unique = true)
    private String email;
    private int age;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;

}
