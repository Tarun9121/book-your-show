package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class AvailableShowDto {
    private UUID id;
    private Integer availableSeats;
    private LocalDate showDate;
    private LocalTime showTime;
}
