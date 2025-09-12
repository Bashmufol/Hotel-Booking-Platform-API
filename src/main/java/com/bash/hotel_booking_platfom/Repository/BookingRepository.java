package com.bash.hotel_booking_platfom.Repository;

import com.bash.hotel_booking_platfom.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository {
    @Query("SELECT r FROM Room r WHERE r.isAvailable = true AND " +
            "NOT EXISTS (SELECT b FROM Booking b WHERE b.room = r AND " +
            "(:checkIn < b.checkOutTime AND :checkOut > b.checkInTime))")
    List<Room> findAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut);
}
