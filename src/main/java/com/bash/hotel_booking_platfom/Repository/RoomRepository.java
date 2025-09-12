package com.bash.hotel_booking_platfom.Repository;

import com.bash.hotel_booking_platfom.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByOwnerId();
}
