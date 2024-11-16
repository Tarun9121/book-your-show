package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookingDetails {
    private UserDto user;
    private ShowResponseDto show;
}
