package com.bash.hotel_booking_platfom.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateRoomRequest(
        String name,
        String description,
        BigDecimal pricePerNight,
        UUID ownerId
) {}
