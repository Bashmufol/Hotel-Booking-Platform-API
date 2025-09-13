package com.bash.hotel_booking_platfom.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private BigDecimal pricePerNight;
    private boolean isAvailable;
    @ManyToOne
    @JoinTable(name = "owner_id")
    private User owner;
}
