package com.bookings.controller;

import com.bookings.dto.BaseResponse;
import com.bookings.dto.MovieDto;
import com.bookings.service.implementation.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moive")
public class MovieController {
    @Autowired
    private MovieServiceImpl movieService;

    @PostMapping("/register")
    public BaseResponse<String> registerMovie(@RequestBody MovieDto movieDto) {
        return movieService.registerMovie(movieDto);
    }
}
