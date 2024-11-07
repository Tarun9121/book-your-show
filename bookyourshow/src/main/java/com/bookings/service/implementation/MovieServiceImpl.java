package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;
import com.bookings.dto.BaseResponse;
import com.bookings.dto.MovieDto;
import com.bookings.entity.Movie;
import com.bookings.exception.ApiException;
import com.bookings.repository.MovieRepository;
import com.bookings.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private Convert transform;

    public BaseResponse<String> registerMovie(MovieDto movieDto) {
        try {
            if(ObjectUtils.isEmpty(movieDto)) {
                throw new ApiException(BookYourShow.DATA_NULL);
            }
            Movie movie = transform.convert(movieDto);
            movieRepository.save(movie);
            log.info(BookYourShow.DATA_SAVED);
            return BaseResponse.success(HttpStatus.CREATED, BookYourShow.DATA_SAVED);
        } catch (Exception e) {
            log.error(BookYourShow.SOMETHING_WENT_WRONG + " {}", e.getMessage());
            return BaseResponse.error(HttpStatus.BAD_REQUEST, BookYourShow.DATA_SAVING_FAILURE);
        }
    }
}
