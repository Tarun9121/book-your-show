package com.bookings.service;

import com.bookings.dto.MovieDto;
import org.springframework.http.ResponseEntity;

public interface MovieService {
    public ResponseEntity<String> registerMovie(MovieDto movieDto);

}
