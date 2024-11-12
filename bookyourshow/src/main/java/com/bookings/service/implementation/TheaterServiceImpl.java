package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;
import com.bookings.convert.TheaterConvert;
import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterDto;
import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import com.bookings.exception.ApiException;
import com.bookings.repository.TheaterRepository;
import com.bookings.service.TheaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TheaterServiceImpl implements TheaterService {
    private TheaterRepository theaterRepository;
    private Convert transform;
    private TheaterConvert theaterConvert;

    @Autowired
    public TheaterServiceImpl(TheaterRepository theaterRepository, Convert convert, TheaterConvert theaterConvert) {
            this.theaterRepository = theaterRepository;
            this.theaterConvert = theaterConvert;
            this.transform = convert;
    }

    public ResponseEntity<String> registerTheater(TheaterDto theaterDto) {
        try {
            if(ObjectUtils.isEmpty(theaterDto)) {
               throw new ApiException(BookYourShow.DATA_NULL);
            }
            Theater theater = transform.convert(theaterDto);
            theaterRepository.save(theater);
            log.info("Theater Details saved");
            return ResponseEntity.status(HttpStatus.CREATED).body(BookYourShow.DATA_SAVED);
        } catch (ApiException api) {
          log.error("{}", api.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(api.getMessage());
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BookYourShow.DATA_SAVING_FAILURE);
        }
    }

    @Override
    public ResponseEntity<String> softDeleteTheater(UUID id) {
        try {
            Theater theater = theaterRepository.findActiveById(id)
                    .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": Theater ID " + id));

            theater.setDeleted(true);
            theaterRepository.save(theater);

            log.info(BookYourShow.DATA_DELETED);
            return ResponseEntity.status(HttpStatus.OK).body(BookYourShow.DATA_DELETED);

        } catch (ApiException e) {
            log.error(BookYourShow.DATA_NOT_FOUND + ": {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            log.error(BookYourShow.SOMETHING_WENT_WRONG + ": {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BookYourShow.SOMETHING_WENT_WRONG);
        }
    }

//    public ResponseEntity<MovieDto> registeredMovies(UUID theaterId) {
//        Theater theater = theaterRepository.findById(theaterId)
//                .orElseThrow(() -> new ApiException(BookYourShow.THEATER_NOT_FOUND));
//        List<TheaterMovie> registeredMovies = theater.getRegisteredMovies();
//        List<MovieDto> registeredMoviesDto = new ArrayList<>();
//        registeredMovies.forEach(theaterMovie -> {
//            MovieDto movieDto = new MovieDto();
//            BeanUtils.copyProperties(theaterMovie.getMovie(), movieDto);
//            registeredMoviesDto.add(movieDto);
//        });
//        return (ResponseEntity<MovieDto>) registeredMoviesDto;
//    }

    public Optional<Theater> getTheaterById(UUID theaterId, Optional optional) {
        return theaterRepository.findById(theaterId);
    }

    public Theater getTheaterById(UUID theaterId) {
        return getTheaterById(theaterId, Optional.empty())
                .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + " with theaterId: " + theaterId));
    }
}
