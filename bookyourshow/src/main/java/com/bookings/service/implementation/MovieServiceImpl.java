package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;
import com.bookings.convert.MovieConvert;
import com.bookings.dto.MovieDto;
import com.bookings.entity.Actor;
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

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private Convert transform;
    @Autowired
    private MovieConvert movieConvert;

    public ResponseEntity<List<MovieDto>> getAllMovies() {
        try {
            List<Movie> movieList = movieRepository.findAllLatestMovies();
            List<MovieDto> movieDtoList = movieConvert.convert(movieList);
            return ResponseEntity.status(HttpStatus.OK).body(movieDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    public ResponseEntity<String> softDeleteMovie(UUID id) {
        try {
            Movie movie = movieRepository.findActiveById(id)
                    .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": Movie ID " + id));

            movie.setDeleted(true);
            movieRepository.save(movie);

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

    @Override
    public ResponseEntity<String> registerMovie(MovieDto movieDto) {
        try {
            if(ObjectUtils.isEmpty(movieDto)) {
                throw new ApiException(BookYourShow.DATA_NULL);
            }
            Movie movie = transform.convert(movieDto);
            movieRepository.save(movie);
            log.info(BookYourShow.DATA_SAVED);
            return ResponseEntity.status(HttpStatus.CREATED).body(BookYourShow.DATA_SAVED);
        } catch (Exception e) {
            log.error(BookYourShow.SOMETHING_WENT_WRONG + " {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BookYourShow.DATA_SAVING_FAILURE);
        }
    }

    public ResponseEntity<MovieDto> getMovieDtoById(UUID movieId) {
        try {
            Movie movie = getMovieById(movieId);
            MovieDto movieDto = transform.convert(movie);
            return ResponseEntity.status(HttpStatus.OK).body(movieDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public Movie getMovieById(UUID movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + " with movieId: " + movieId));
    }
}