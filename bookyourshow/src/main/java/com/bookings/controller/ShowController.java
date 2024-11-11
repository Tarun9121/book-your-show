package com.bookings.controller;

import com.bookings.dao.ShowDao;
import com.bookings.dto.AvailableTheatersDto;
import com.bookings.dto.ShowDto;
import com.bookings.dto.ShowRequestDto;
import com.bookings.service.ShowService;
import com.bookings.service.implementation.ShowServiceImpl;
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
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowServiceImpl showService;


    @PostMapping("/create/{theaterId}/{movieId}")
    public ResponseEntity<String> createShows(
            @PathVariable UUID theaterId,
            @PathVariable UUID movieId,
            @RequestBody List<ShowRequestDto> showRequestDtos) {
        return showService.createShow(theaterId, movieId, showRequestDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDto> getShowById(@PathVariable UUID id) {
        return showService.getShowResponseById(id);
    }

    @GetMapping("/search/{movieId}")
    public List<AvailableTheatersDto> availableShows(@PathVariable("movieId") UUID movieId) {
        return showService.availableShows01(movieId);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable UUID id) {
        return showService.deleteShow(id);
    }
}

