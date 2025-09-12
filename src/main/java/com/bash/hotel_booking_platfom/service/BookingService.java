package com.bash.hotel_booking_platfom.service;

import com.bash.hotel_booking_platfom.Repository.BookingRepository;
import com.bash.hotel_booking_platfom.Repository.RoomRepository;
import com.bash.hotel_booking_platfom.model.Booking;
import com.bash.hotel_booking_platfom.model.Room;
import com.bash.hotel_booking_platfom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public Booking bookRoom(long roomId, User user, LocalDateTime checkIn, LocalDateTime checkOut) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        boolean isAvailable = bookingRepository.findConflictingBookings(roomId, checkIn, checkOut).isEmpty();
        if (!isAvailable) {
            throw new IllegalArgumentException("Room not available for selected dates");
        }
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        return bookingRepository.save(booking);
    }
}
