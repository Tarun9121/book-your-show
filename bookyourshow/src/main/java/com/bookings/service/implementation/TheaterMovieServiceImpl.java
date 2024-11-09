package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;
import com.bookings.convert.TheaterMovieConvert;
import com.bookings.dto.MovieDto;
import com.bookings.dto.TheaterMovieDto;
import com.bookings.dto.TheaterMovieRequestBodyDto;
import com.bookings.entity.Movie;
import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import com.bookings.exception.ApiException;
import com.bookings.repository.MovieRepository;
import com.bookings.repository.TheaterMovieRepository;
import com.bookings.repository.TheaterRepository;
import com.bookings.service.TheaterMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TheaterMovieServiceImpl implements TheaterMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private TheaterMovieRepository theaterMovieRepository;
    @Autowired
    private TheaterMovieConvert transform;



    @Override
    public ResponseEntity<String> registerMovieAndTheater(UUID movieId, List<UUID> theaterIds) {
        try {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new ApiException("Movie not found with ID: " + movieId));

            List<UUID> notRecognizedTheaters = new ArrayList<>();

            for (UUID theaterId : theaterIds) {
                Optional<Theater> theaterOpt = theaterRepository.findById(theaterId);

                if (theaterOpt.isPresent()) {
                    Theater theater = theaterOpt.get();
                    TheaterMovie theaterMovie = new TheaterMovie();
                    theaterMovie.setMovie(movie);
                    theaterMovie.setTheater(theater);
                    theaterMovieRepository.save(theaterMovie);
                } else {
                    notRecognizedTheaters.add(theaterId);
                }
            }

            log.warn("These TheaterId's are not recognized: {}", notRecognizedTheaters);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movie successfully registered with theaters");

        } catch (ApiException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}