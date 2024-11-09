package com.bookings.controller;

import com.bookings.dto.MovieDto;
import com.bookings.service.implementation.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieServiceImpl movieService;

    @PostMapping("/register")
    public ResponseEntity<String> registerMovie(@RequestBody MovieDto movieDto) {
        return movieService.registerMovie(movieDto);
    }
}