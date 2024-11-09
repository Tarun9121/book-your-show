package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.dao.ShowDao;
import com.bookings.dao.TheaterMovieDao;
import com.bookings.dto.AvailableShowDto;
import com.bookings.dto.AvailableTheatersDto;
import com.bookings.dto.ShowRequestDto;
import com.bookings.dto.ShowDto;
import com.bookings.entity.Movie;
import com.bookings.entity.Show;
import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import com.bookings.exception.ApiException;
import com.bookings.repository.MovieRepository;
import com.bookings.repository.ShowRepository;
import com.bookings.repository.TheaterMovieRepository;
import com.bookings.repository.TheaterRepository;
import com.bookings.service.ShowService;
import com.bookings.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterMovieRepository theaterMovieRepository;

    @Autowired
    private ShowDao showDao;

    @Autowired
    private TheaterRepository theaterRepository;  // Add this

    @Autowired
    private TheaterMovieDao theaterMovieDao;



    @Autowired
    private MovieRepository movieRepository;  // And this
    @Autowired
    private Convert convert;

    @Override
    public ResponseEntity<String> createShow(UUID theaterId, UUID movieId, List<ShowRequestDto> showRequestDtos) {
        try {
            Theater theater = theaterRepository.findById(theaterId)
                    .orElseThrow(() -> new ApiException("Theater not found with ID: " + theaterId));

            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new ApiException("Movie not found with ID: " + movieId));

            TheaterMovie theaterMovie = theaterMovieRepository.findByTheaterAndMovie(theater, movie)
                    .orElseThrow(() -> new ApiException("TheaterMovie association not found between Theater and Movie"));

            for (ShowRequestDto showRequestDto : showRequestDtos) {
                Show show = new Show();
                show.setTheaterMovie(theaterMovie);
                show.setShowDate(showRequestDto.getShowDate());
                show.setShowTime(showRequestDto.getShowTime());

                // Save each show individually
                showRepository.save(show);
            }

            log.info("Shows created successfully for Theater ID: {} and Movie ID: {}", theaterId, movieId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Shows created successfully");

        } catch (ApiException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create shows");
        }
    }

    @Override
    public ResponseEntity<ShowDto> getShowById(UUID id) {
        ShowDto showDto = new ShowDto();
        try {
            Show show = showRepository.findById(id)
                    .orElseThrow(() -> new ApiException("Show not found with ID: " + id));
            showDto = convert(show);
            return ResponseEntity.status(HttpStatus.OK).body(showDto);
        } catch (ApiException e) {
            log.error("Validation error: {}", e.getMessage());
            showDto.setMessage("Show Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(showDto);
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            showDto.setMessage("Unexpected Error occured");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(showDto);
        }
    }

    public ShowDto convert(Show show) {
        ShowDto showDto = new ShowDto();
        if (!ObjectUtils.isEmpty(show)) {
            showDto.setId(show.getId());
            showDto.setId(show.getTheaterMovie().getId());
            showDto.setShowTime(show.getShowTime());
        }
        return showDto;
    }

    @Override
    public ResponseEntity<String> deleteShow(UUID id) {
        try {
            showRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Show deleted successfully");
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete show");
        }
    }

    @Override
    public ResponseEntity<List<AvailableTheatersDto>> availableShows(UUID movieId) {
        try {
            List<TheaterMovie> availableTheaterIds = theaterMovieRepository.findByMovieId(movieId);
            List<AvailableTheatersDto> availableTheatersResponse = new ArrayList<>();

            availableTheaterIds.forEach(theaterId -> {
                UUID theaterMovieId = theaterMovieDao.findId(theaterId.getTheater().getId(), movieId);
                if(ObjectUtils.isEmpty(theaterMovieId))
                    throw new ApiException(BookYourShow.THEATER_NOT_REGISTERED);
                List<Show> availableShows = showDao.availableShowsByTheaterMovieId(theaterMovieId);
                Theater theater = theaterRepository.findById(theaterId.getTheater().getId())
                        .orElseThrow(() -> new ApiException(BookYourShow.THEATER_NOT_FOUND));
                AvailableTheatersDto availableTheater = new AvailableTheatersDto();

                if(!ObjectUtils.isEmpty(theater)) {
                    BeanUtils.copyProperties(theater, availableTheater);
                    List<AvailableShowDto> listOfShows = new ArrayList<>();

                    availableShows.forEach(show -> {
                        AvailableShowDto showDto = new AvailableShowDto();
                        if(!ObjectUtils.isEmpty(show)) {
                            BeanUtils.copyProperties(show, showDto);
                            listOfShows.add(showDto);
                        }
                    });
                    availableTheater.setAvailableShows(listOfShows);
                    availableTheatersResponse.add(availableTheater);
                }
            });

            return ResponseEntity.status(HttpStatus.OK).body(availableTheatersResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
}
