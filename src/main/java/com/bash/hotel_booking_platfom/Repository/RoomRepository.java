package com.bash.hotel_booking_platfom.Repository;

import com.bash.hotel_booking_platfom.model.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
        @Query("SELECT r FROM Room r WHERE r.isAvailable = true AND " +
                "NOT EXISTS (SELECT b FROM Booking b WHERE b.room = r AND " +
                "(:checkIn < b.checkOutDate AND :checkOut > b.checkInDate))")
        List<Room> findAvailableRooms(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

        @Lock(LockModeType.PESSIMISTIC_WRITE)
        @Query("SELECT r FROM Room r WHERE r.id = :roomId")
        Optional<Room> findByIdForUpdate(@Param("roomId") long roomId);
}
