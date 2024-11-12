package com.bookings.controller;

import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterDto;
import com.bookings.entity.Theater;
import com.bookings.service.TheaterService;
import com.bookings.service.implementation.TheaterServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterServiceImpl theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerTheater(@RequestBody TheaterDto theaterDto) {
        return theaterService.registerTheater(theaterDto);
    }

    @Operation(summary = "Disable Theater", description = "Soft delete a Theater by setting the isDeleted flag")
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteTheater(@PathVariable UUID id) {
        return theaterService.softDeleteTheater(id);
    }

//    @GetMapping("/{theaterId}")
//    public ResponseEntity<MovieDto> registeredMovies(@PathVariable("theaterId")UUID theaterId) {
//        return theaterService.registeredMovies(theaterId);
//    }
}
