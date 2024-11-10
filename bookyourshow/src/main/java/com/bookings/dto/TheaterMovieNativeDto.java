package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class TheaterMovieNativeDto {
    private UUID id;
    private UUID theaterId;
    private UUID movieId;
}
