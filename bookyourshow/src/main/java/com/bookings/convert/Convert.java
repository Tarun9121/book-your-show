package com.bookings.convert;

import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterDto;
import com.bookings.dto.TheaterMovieDto;
import com.bookings.dto.UserDto;
import com.bookings.dto.UserRequestDto;
import com.bookings.entity.Movie;
import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import com.bookings.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class Convert {
    public Movie convert(MovieDto movieDto) {
        Movie movie = new Movie();
        if(!ObjectUtils.isEmpty(movieDto)) {
            BeanUtils.copyProperties(movieDto, movie);
        }
        return movie;
    }

    public UserRequestDto convertToRequesDto(User user) {
        UserRequestDto userRequestDto = new UserRequestDto();

        if(!ObjectUtils.isEmpty(user)) {
            BeanUtils.copyProperties(user, userRequestDto);
        }
        return userRequestDto;
    }

    public User convert(UserRequestDto userRequestDto) {
        User user = new User();

        if(!ObjectUtils.isEmpty(userRequestDto)) {
            BeanUtils.copyProperties(userRequestDto, user);
        }
        return user;
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

    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        if (!ObjectUtils.isEmpty(user)) {
            BeanUtils.copyProperties(user, userDto);
        }
        return userDto;
    }

    public List<MovieDto> convert(List<Movie> movieList) {
        List<MovieDto> movieDtoList = new ArrayList<>();
        if(CollectionUtils.isEmpty(movieList)) {
            BeanUtils.copyProperties(movieList, movieDtoList);
        }
        return movieDtoList;
    }

    public static User convert(UserDto userDto) {
        User user = new User();
        if (!ObjectUtils.isEmpty(userDto)) {
            BeanUtils.copyProperties(userDto, user);
        }
        return user;
    }
}
