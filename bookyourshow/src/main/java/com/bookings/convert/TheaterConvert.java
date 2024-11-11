package com.bookings.convert;

import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterDto;
import com.bookings.entity.Theater;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class TheaterConvert {
    public TheaterDto convert(Theater theater) {
        TheaterDto theaterDto = new TheaterDto();
        if(!ObjectUtils.isEmpty(theater)) {
            BeanUtils.copyProperties(theater, theaterDto);
        }
        return theaterDto;
    }
}
