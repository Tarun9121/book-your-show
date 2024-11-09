package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class MovieDto  {
    private UUID id;
    private String name;
    private String genre;
    private String description;
    private LocalDate releaseDate;
    private Double rating;
    private String duration;
}
