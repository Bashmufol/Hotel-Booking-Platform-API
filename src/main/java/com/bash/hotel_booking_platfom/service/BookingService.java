package com.bash.hotel_booking_platfom.service;

import com.bash.hotel_booking_platfom.Repository.BookingRepository;
import com.bash.hotel_booking_platfom.Repository.RoomRepository;
import com.bash.hotel_booking_platfom.Repository.UserRepository;
import com.bash.hotel_booking_platfom.exception.ResourceNotFoundException;
import com.bash.hotel_booking_platfom.model.Booking;
import com.bash.hotel_booking_platfom.model.Room;
import com.bash.hotel_booking_platfom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public Booking bookRoom(long roomId, UUID userId, LocalDate checkIn, LocalDate checkOut) {

        // Lock the room record for update to prevent race conditions and double-bookings
        Room room = roomRepository.findByIdForUpdate(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // Ensure the user exists before proceeding with the booking
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        // Obtain a reference (proxy) to the User entity without fully loading it
        User userRef = userRepository.getReferenceById(userId);

        // Check if there are any overlapping bookings for the selected dates
        boolean isAvailable = bookingRepository.findConflictingBookings(roomId, checkIn, checkOut).isEmpty();
        if (!isAvailable) {
            throw new IllegalArgumentException("Room not available for selected dates");
        }

        // Create and persist the booking
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(userRef);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        return bookingRepository.save(booking);
    }
}
