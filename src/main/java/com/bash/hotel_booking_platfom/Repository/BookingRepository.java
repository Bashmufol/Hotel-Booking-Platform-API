package com.bash.hotel_booking_platfom.Repository;

import com.bash.hotel_booking_platfom.model.Booking;
import com.bash.hotel_booking_platfom.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT r FROM Room r WHERE r.isAvailable = true AND " +
            "NOT EXISTS (SELECT b FROM Booking b WHERE b.room = r AND " +
            "(:checkIn < b.checkOutDate AND :checkOut > b.checkInDate))")
    List<Room> findAvailableRooms(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND (:checkIn < b.checkOutDate AND :checkOut > b.checkInDate)")
    List<Booking> findConflictingBookings(@Param("roomId") long roomId,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);
}
