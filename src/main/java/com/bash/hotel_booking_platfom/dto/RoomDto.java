package com.bash.hotel_booking_platfom.dto;

import com.bash.hotel_booking_platfom.model.Room;

import java.math.BigDecimal;

public record RoomDto(
        long id,
        String name,
        String description,
        BigDecimal pricePerNight
) {
    public RoomDto(Room room){
        this(room.getId(), room.getName(), room.getDescription(), room.getPricePerNight());
    }
}
