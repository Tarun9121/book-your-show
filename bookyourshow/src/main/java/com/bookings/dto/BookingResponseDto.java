package com.bookings.dto;

import com.bookings.entity.Show;
import com.bookings.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookingResponseDto {
    private UUID id;
    private Show show = new Show();
}
