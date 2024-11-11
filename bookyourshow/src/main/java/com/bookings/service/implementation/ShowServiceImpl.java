package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;
import com.bookings.convert.ShowConvert;
import com.bookings.convert.TheaterConvert;
import com.bookings.dao.ShowDao;
import com.bookings.dao.TheaterMovieDao;
import com.bookings.dto.AvailableShowDto;
import com.bookings.dto.AvailableTheatersDto;
import com.bookings.dto.ShowDto;
import com.bookings.dto.ShowRequestDto;
import com.bookings.dto.TheaterMovieNativeDto;
import com.bookings.entity.Movie;
import com.bookings.entity.Show;
import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import com.bookings.exception.ApiException;
import com.bookings.repository.ShowRepository;
import com.bookings.service.ShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final ShowDao showDao;
    private final TheaterServiceImpl theaterService;
    private final TheaterMovieDao theaterMovieDao;
    private final BookingUtility bookingUtility;
    private final TheaterMovieServiceImpl theaterMovieService;
    private final MovieServiceImpl movieService;
    private final Convert convert;
    private final TheaterConvert theaterConvert;
    private final ShowConvert showConvert;

    @Autowired
    public ShowServiceImpl(
            TheaterConvert theaterConvert,
            BookingUtility bookingUtility,
            ShowRepository showRepository,
            ShowDao showDao,
            TheaterServiceImpl theaterService,
            TheaterMovieDao theaterMovieDao,
            TheaterMovieServiceImpl theaterMovieService,
            MovieServiceImpl movieService,
            ShowConvert showConvert,
            Convert convert) {
        this.showConvert = showConvert;
        this.theaterConvert = theaterConvert;
        this.bookingUtility = bookingUtility;
        this.showRepository = showRepository;
        this.showDao = showDao;
        this.theaterService = theaterService;
        this.theaterMovieDao = theaterMovieDao;
        this.theaterMovieService = theaterMovieService;
        this.movieService = movieService;
        this.convert = convert;
    }

    @Transactional
    @Modifying
    public void updateShow(Show show) {
        showRepository.save(show);
        log.info("Show: available seats updated {}", show.getAvailableSeats());
    }

    @Override
    public ResponseEntity<String> createShow(UUID theaterId, UUID movieId, List<ShowRequestDto> showRequestDtos) {
        try {
            Theater theater = theaterService.getTheaterById(theaterId);
            Movie movie = movieService.getMovieById(movieId);
            TheaterMovie theaterMovie = theaterMovieService.getTheaterMovie(theater, movie);

            for (ShowRequestDto showRequestDto : showRequestDtos) {
                Show show = new Show();
                show.setTheaterMovie(theaterMovie);
                show.setAvailableSeats(theater.getNoOfSeats());

                log.warn("movie release date: {}", movie.getReleaseDate());
                log.warn("Show date: {}", showRequestDto.getShowDate());
                log.warn("isAfter: {}", movie.getReleaseDate().isAfter(showRequestDto.getShowDate()));

                if(movie.getReleaseDate().isAfter(showRequestDto.getShowDate())) {
                    throw new ApiException("Movie Not Released Yet");
                }
                show.setShowDate(showRequestDto.getShowDate());
                show.setShowTime(showRequestDto.getShowTime());

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
    public ResponseEntity<ShowDto> getShowResponseById(UUID id) {
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
            List<TheaterMovieNativeDto> associatedTheaters = theaterMovieService.getAssociatedTheaters(movieId);
            List<AvailableTheatersDto> availableTheatersResponse = new ArrayList<>();

            associatedTheaters.forEach(theaterMovie -> {
                UUID theaterMovieId = theaterMovie.getId();

                if(theaterMovieId == null || theaterMovieId.equals(new UUID(0, 0))) {
                    throw new ApiException(BookYourShow.THEATER_NOT_REGISTERED);
                }

                List<Show> availableShows = showDao.availableShowsByTheaterMovieId(theaterMovieId);
                availableShows.forEach(show -> {
                    show.setTheaterMovie(null);
                });
                Theater theater = theaterService.getTheaterById(theaterMovie.getTheaterId());

                if(ObjectUtils.isEmpty(theater)) {
                    throw new ApiException(BookYourShow.THEATER_NOT_FOUND);
                }
                AvailableTheatersDto availableTheater = new AvailableTheatersDto();

                if(!ObjectUtils.isEmpty(theater)) {
                    BeanUtils.copyProperties(theater, availableTheater);
                    List<AvailableShowDto> listOfShows = new ArrayList<>();

                    availableShows.forEach(show -> {
                        AvailableShowDto showDto = new AvailableShowDto();
                        if(!ObjectUtils.isEmpty(show)) {
                            BeanUtils.copyProperties(show, showDto);
                            log.info("total no of seats: {}", theater.getNoOfSeats());
                            log.info("{}", bookingUtility.seatsBooked(show.getId()));
                            System.out.println("total no of seats: " + theater.getNoOfSeats());
                            System.out.println("total booked seats: " + bookingUtility.seatsBooked(show.getId()));
                            Integer avilableSeats = theater.getNoOfSeats() - bookingUtility.seatsBooked(show.getId());
                            showDto.setAvailableSeats(avilableSeats);
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

    public List<AvailableTheatersDto> availableShows01(UUID movieId) {
        try {
            List<TheaterMovie> availableTheaters = theaterMovieService.getAssociatedTheaters01(movieId);
            List<AvailableTheatersDto> availableTheatersResponse = new ArrayList<>();

            availableTheaters.forEach(theaterMovie -> {
                UUID theaterMovieId = theaterMovie.getId();

                if(theaterMovieId == null || theaterMovieId.equals(new UUID(0, 0))) {
                    throw new ApiException(BookYourShow.THEATER_NOT_REGISTERED);
                }

                List<Show> availableShows = showRepository.findByTheaterMovie(theaterMovie);
                AvailableTheatersDto availableTheatersDto = new AvailableTheatersDto();

                availableTheatersDto.setTheater(theaterConvert.convert(theaterMovie.getTheater()));
                availableTheatersDto.setAvailableShows(showConvert.convert(availableShows));

//                availableTheatersDto.getAvailableShows().forEach(show -> {
//                    Integer availableSeats = theaterMovie.getTheater().getNoOfSeats() - bookingUtility.seatsBooked(show.getId());
//                    show.setSeatsAvailable(availableSeats);
//                });

                availableTheatersResponse.add(availableTheatersDto);
            });

            return availableTheatersResponse;
        } catch(ApiException api) {
            throw new ApiException(BookYourShow.SOMETHING_WENT_WRONG);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Show getShowById(UUID showId) {
        return showRepository.findById(showId)
                .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": Show ID " + showId));
    }
}
