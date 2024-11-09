package com.bookings.convert;

import com.bookings.dto.TheaterMovieDto;
import com.bookings.entity.TheaterMovie;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class TheaterMovieConvert {
    public List<TheaterMovieDto> convert(List<TheaterMovie> theaterMovieList) {
        List<TheaterMovieDto> theaterMovieDtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(theaterMovieList)) {
            BeanUtils.copyProperties(theaterMovieList, theaterMovieDtoList);
        }
        return theaterMovieDtoList;
    }
}
