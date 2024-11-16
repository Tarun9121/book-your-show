package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class ShowDetails extends BaseResponseDto {
    private UUID id;
    private TheaterDto theater;
    private MovieDto movie;
    private LocalDate showDate;
    private LocalTime showTime;
}
