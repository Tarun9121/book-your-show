package com.bookings.controller;

import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterMovieDto;
import com.bookings.dto.TheaterMovieRequestBodyDto;
import com.bookings.service.implementation.TheaterMovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/theater-movie")
public class TheaterMovieController {

    @Autowired
    private TheaterMovieServiceImpl theaterMovieService;

    @PostMapping("/register")
    public ResponseEntity<String> registerMovieAndTheater(@RequestBody TheaterMovieRequestBodyDto theaterMovieDto) {
        return theaterMovieService.registerMovieAndTheater(theaterMovieDto.getMovieId(), theaterMovieDto.getTheaterIds());
    }

//    @GetMapping("/{theaterId}")
//    public ResponseEntity<List<TheaterMovieDto>> registeredMovies(@PathVariable("theaterId")UUID theaterId) {
//        return theaterMovieService.registeredMovies(theaterId);
//    }
}