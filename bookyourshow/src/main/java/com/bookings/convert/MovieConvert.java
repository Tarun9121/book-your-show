package com.bookings.convert;

import com.bookings.dto.MovieDto;
import com.bookings.entity.Movie;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MovieConvert {
    public List<MovieDto> convert(List<Movie> movieList) {
        List<MovieDto> movieDtoList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(movieList)) {
            movieList.forEach(movie -> {
                MovieDto movieDto = new MovieDto();
                BeanUtils.copyProperties(movie, movieDto);
                movieDtoList.add(movieDto);
            });
        }
        return movieDtoList;
    }
}
