package com.bash.hotel_booking_platfom.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class ResponseModel<X> {
    private String message;
    private int code;
    private X data;
}
