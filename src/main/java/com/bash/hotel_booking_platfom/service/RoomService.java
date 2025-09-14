package com.bash.hotel_booking_platfom.service;

import com.bash.hotel_booking_platfom.Repository.RoomRepository;
import com.bash.hotel_booking_platfom.Repository.UserRepository;
import com.bash.hotel_booking_platfom.dto.CreateRoomRequest;
import com.bash.hotel_booking_platfom.exception.ResourceNotFoundException;
import com.bash.hotel_booking_platfom.model.Booking;
import com.bash.hotel_booking_platfom.model.Room;
import com.bash.hotel_booking_platfom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    public Room createRoom(CreateRoomRequest request) {

        // Ensure the owner exists before creating a room
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Build the new room entity with default availability set to true
        Room room = new Room();
        room.setName(request.name());
        room.setDescription(request.description());
        room.setPricePerNight(request.pricePerNight());
        room.setOwner(owner);
        room.setAvailable(true);

        return roomRepository.save(room);
    }

    public List<Room> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut) {

        // Prevent invalid date ranges (check-in cannot be same as or after check-out)
        if(checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }


    public Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    public String deleteRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        roomRepository.delete(room);
        return "Room deleted";
    }


}
