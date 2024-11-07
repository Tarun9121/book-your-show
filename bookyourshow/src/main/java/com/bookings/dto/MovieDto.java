package com.bookings.dto;

import java.time.LocalDate;
import java.util.UUID;

public class MovieDto {
    private UUID id;
    private String name;
    private String genre;
    private String description;
    private LocalDate releaseDate;
    private Double rating;
    private String duration;
}
