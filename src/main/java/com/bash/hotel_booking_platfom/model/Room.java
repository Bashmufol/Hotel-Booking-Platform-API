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
    @Column(name = "price_per_night", precision = 10, scale = 2, nullable = false)
    private BigDecimal pricePerNight;
    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}
