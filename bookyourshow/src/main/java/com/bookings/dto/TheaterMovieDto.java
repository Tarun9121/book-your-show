package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class TheaterMovieDto {
    private UUID id;
    private MovieDto movie;
    private TheaterDto theaterDto;
}
