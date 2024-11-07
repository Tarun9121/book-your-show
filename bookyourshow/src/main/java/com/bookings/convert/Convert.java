package com.bookings.convert;

import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterDto;
import com.bookings.entity.Movie;
import com.bookings.entity.Theater;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class Convert {
    public Movie convert(MovieDto movieDto) {
        Movie movie = new Movie();
        if(!ObjectUtils.isEmpty(movieDto)) {
            BeanUtils.copyProperties(movieDto, movie);
        }
        return movie;
    }

    public MovieDto convert(Movie movie) {
        MovieDto movieDto = new MovieDto();
        if(!ObjectUtils.isEmpty(movie)) {
            BeanUtils.copyProperties(movie, movieDto);
        }
        return movieDto;
    }

    public Theater convert(TheaterDto theaterDto) {
        Theater theater = new Theater();
        if (!ObjectUtils.isEmpty(theaterDto)) {
            BeanUtils.copyProperties(theaterDto, theater);
        }
        return theater;
    }
}
