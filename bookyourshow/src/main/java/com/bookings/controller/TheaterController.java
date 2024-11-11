package com.bookings.controller;

import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterDto;
import com.bookings.entity.Theater;
import com.bookings.service.implementation.TheaterServiceImpl;
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
@RequestMapping("/theater")
public class TheaterController {
    @Autowired
    private TheaterServiceImpl theaterService;

    @PostMapping("/register")
    public ResponseEntity<String> registerTheater(@RequestBody TheaterDto theaterDto) {
        return theaterService.registerTheater(theaterDto);
    }

//    @GetMapping("/{theaterId}")
//    public ResponseEntity<MovieDto> registeredMovies(@PathVariable("theaterId")UUID theaterId) {
//        return theaterService.registeredMovies(theaterId);
//    }
}
