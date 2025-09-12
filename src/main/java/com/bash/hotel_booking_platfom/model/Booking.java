package com.bash.hotel_booking_platfom.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Room room;
    @ManyToOne
    private User user;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}
