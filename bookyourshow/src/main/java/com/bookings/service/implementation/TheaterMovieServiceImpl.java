package com.bookings.service.implementation;

import com.bookings.convert.TheaterMovieConvert;
import com.bookings.dao.TheaterMovieDao;
import com.bookings.dto.TheaterMovieNativeDto;
import com.bookings.entity.Movie;
import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import com.bookings.exception.ApiException;
import com.bookings.repository.TheaterMovieRepository;
import com.bookings.service.TheaterMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TheaterMovieServiceImpl implements TheaterMovieService {

    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private TheaterMovieRepository theaterMovieRepository;
    @Autowired
    private TheaterMovieConvert transform;
    @Autowired
    private TheaterServiceImpl theaterService;
    @Autowired
    private TheaterMovieDao theaterMovieDao;

    @Override
    public ResponseEntity<String> registerMovieAndTheater(UUID movieId, List<UUID> theaterIds) {
        try {
            Movie movie = movieService.getMovieById(movieId);
            List<UUID> notRecognizedTheaters = new ArrayList<>();

            for (UUID theaterId : theaterIds) {
                Optional<Theater> theaterOpt = theaterService.getTheaterById(theaterId, Optional.empty());

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

    public TheaterMovie getTheaterMovie(Theater theater, Movie movie) {
        return theaterMovieRepository.findByTheaterAndMovie(theater, movie)
                .orElseThrow(() -> new ApiException("TheaterMovie association not found between Theater and Movie"));
    }

    public List<TheaterMovieNativeDto> getAssociatedTheaters(UUID movieId) {
        List<TheaterMovieNativeDto> list = theaterMovieDao.getAssociatedTheaters(movieId);
        return list;
    }
}