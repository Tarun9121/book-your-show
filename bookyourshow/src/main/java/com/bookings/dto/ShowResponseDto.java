package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class ShowResponseDto {
    private TheaterDto theater;
    private LocalDate showDate;
    private LocalTime showTime;
}