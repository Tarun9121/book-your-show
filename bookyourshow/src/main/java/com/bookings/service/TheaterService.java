package com.bookings.service;

import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TheaterService {
    public ResponseEntity<String> registerTheater(TheaterDto theaterDto);
//    public ResponseEntity<MovieDto> registeredMovies(UUID theaterId);
    ResponseEntity<String> softDeleteTheater(UUID id);
}
