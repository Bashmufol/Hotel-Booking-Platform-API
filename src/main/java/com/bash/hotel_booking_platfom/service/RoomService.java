package com.bash.hotel_booking_platfom.service;

import com.bash.hotel_booking_platfom.Repository.RoomRepository;
import com.bash.hotel_booking_platfom.model.Room;
import com.bash.hotel_booking_platfom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room createRoom(Room room, User owner) {
        room.setOwner(owner);
        room.setAvailable(true);
        return roomRepository.save(room);
    }


    public Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }
}
