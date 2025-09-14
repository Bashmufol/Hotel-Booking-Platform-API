package com.bash.hotel_booking_platfom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
