package com.bash.hotel_booking_platfom.controller;

import com.bash.hotel_booking_platfom.dto.CreateRoomRequest;
import com.bash.hotel_booking_platfom.model.Booking;
import com.bash.hotel_booking_platfom.model.Room;
import com.bash.hotel_booking_platfom.service.BookingService;
import com.bash.hotel_booking_platfom.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {
    private final RoomService roomService;
    private final BookingService bookingService;

    @PostMapping("/create")
    public Room createRoom(@RequestBody CreateRoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping("/available")
    public List<Room> searchAvailableRooms(
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut) {
        return roomService.searchAvailableRooms(checkIn, checkOut);
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable long id) {
        return roomService.findRoomById(id);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable long id) {
        return roomService.deleteRoomById(id);
    }

    // Endpoint to book a room by a given user and within given date range
    @PostMapping("/book-room")
    public Booking bookRoom(long roomId, UUID userId, LocalDate checkIn, LocalDate checkOut) {
        return bookingService.bookRoom(roomId, userId, checkIn, checkOut);
    }


}
