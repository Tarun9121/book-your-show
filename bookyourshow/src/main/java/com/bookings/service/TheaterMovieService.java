package com.bookings.service;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TheaterMovieService {
    public ResponseEntity<String> registerMovieAndTheater(UUID movieId, List<UUID> theaterIds);
}
