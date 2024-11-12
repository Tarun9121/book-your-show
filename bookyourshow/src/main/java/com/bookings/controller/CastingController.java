package com.bookings.controller;

import com.bookings.dto.CastingDto;
import com.bookings.entity.Actor;
import com.bookings.entity.Casting;
import com.bookings.service.implementation.CastingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/casting")
public class CastingController {

    private final CastingServiceImpl castingService;

    @Autowired
    public CastingController(CastingServiceImpl castingService) {
        this.castingService = castingService;
    }

    @PostMapping("/add")
    public ResponseEntity<Casting> addCastingToMovie(@RequestBody CastingDto castingDto) {
        return castingService.addCastingToMovie(castingDto);
    }

    @GetMapping("/actors/{movieId}")
    public ResponseEntity<List<Actor>> getActorsByMovieId(@PathVariable UUID movieId) {
        return castingService.getActorsByMovieId(movieId);
    }
}
