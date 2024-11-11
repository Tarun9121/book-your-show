package com.bookings.service;

import com.bookings.dto.MovieDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface MovieService {
    public ResponseEntity<String> registerMovie(MovieDto movieDto);
    public ResponseEntity<String> softDeleteMovie(UUID id);

}
