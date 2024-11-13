package com.bookings.controller;

import com.bookings.dto.MovieDto;
import com.bookings.service.MovieService;
import com.bookings.service.implementation.MovieServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerMovie(@RequestBody MovieDto movieDto) {
        return movieService.registerMovie(movieDto);
    }

    @GetMapping("/get-movies")
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/search-movies")
    public ResponseEntity<List<MovieDto>> searchMovie(@RequestParam("search") String search) {
        return movieService.searchMovies(search);
    }

    @GetMapping("/get-movies/{movieId}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable("movieId") UUID movieId) {
        return movieService.getMovieDtoById(movieId);
    }

    @Operation(summary = "Soft Delete Movie", description = "Soft delete a Movie by setting the isDeleted flag")
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteMovie(@PathVariable UUID id) {
        return movieService.softDeleteMovie(id);
    }

//    public void addActors(@RequestBody Casting casting) {
//
//    }
}