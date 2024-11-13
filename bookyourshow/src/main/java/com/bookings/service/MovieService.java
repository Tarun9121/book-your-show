package com.bookings.service;

import com.bookings.dto.MovieDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    public ResponseEntity<String> registerMovie(MovieDto movieDto);
    public ResponseEntity<String> softDeleteMovie(UUID id);
    public ResponseEntity<List<MovieDto>> getAllMovies();
    public ResponseEntity<MovieDto> getMovieDtoById(UUID movieId);
    public ResponseEntity<List<MovieDto>> searchMovies(String name);
}
