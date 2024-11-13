package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.BookingConvert;
import com.bookings.dto.BookingDto;
import com.bookings.dto.BookingRequestDto;
import com.bookings.dto.BookingResponseDto;
import com.bookings.entity.Booking;
import com.bookings.entity.Show;
import com.bookings.entity.User;
import com.bookings.exception.ApiException;
import com.bookings.repository.BookingRepository;
import com.bookings.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TheaterServiceImpl theaterService;
    private final BookingConvert bookingConvert;
    private final UserServiceImpl userService;
    private final ShowServiceImpl showService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,UserServiceImpl userService, ShowServiceImpl showService,BookingConvert convert, TheaterServiceImpl theaterService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.showService = showService;
        this.bookingConvert = convert;
        this.theaterService = theaterService;
    }


    @Override
    public ResponseEntity<String> createBooking(BookingRequestDto bookingRequestDto) {
        try {
            if(ObjectUtils.isEmpty(bookingRequestDto)) {
                throw new ApiException(BookYourShow.DATA_NULL);
            }

            User user = userService.getUserById(bookingRequestDto.getUserId());
            Show show = showService.getShowById(bookingRequestDto.getShowId());

            if(bookingRequestDto.getNoOfSeatsSelected() >= show.getAvailableSeats()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BookYourShow.SEATS_NOT_AVAILABLE);
            }

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setShow(show);
            booking.setNoOfSeatsSelected(bookingRequestDto.getNoOfSeatsSelected());

            bookingRepository.save(booking);
            show.setAvailableSeats(show.getAvailableSeats() - booking.getNoOfSeatsSelected());

            showService.updateShow(show);
            log.info(BookYourShow.DATA_SAVED + " for User ID: {}", bookingRequestDto.getUserId());

            return ResponseEntity.status(HttpStatus.CREATED).body(BookYourShow.DATA_SAVED);

        } catch (ApiException e) {
            log.error(BookYourShow.DATA_SAVING_FAILURE + ": {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error(BookYourShow.SOMETHING_WENT_WRONG + ": {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BookYourShow.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<BookingDto> getBookingById(UUID id) {
        BookingDto bookingDto = new BookingDto();
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": Booking ID " + id));

            bookingDto = bookingConvert.convert(booking);
            return ResponseEntity.ok(bookingDto);

        } catch (ApiException e) {
            log.error(BookYourShow.DATA_NOT_FOUND + ": {}", e.getMessage());
            bookingDto.setStatus(HttpStatus.NOT_FOUND);
            bookingDto.setMessage(BookYourShow.DATA_NOT_FOUND);
            bookingDto.setLocalDateTime(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bookingDto);
        } catch (Exception e) {
            log.error(BookYourShow.SOMETHING_WENT_WRONG + ": {}", e.getMessage());
            bookingDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            bookingDto.setMessage(BookYourShow.DATA_NOT_FOUND);
            bookingDto.setLocalDateTime(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bookingDto);
        }
    }

    public ResponseEntity<List<BookingDto>> getBookingsByUserId(UUID userId) {
        try {
            List<Booking> bookingListOfUser = bookingRepository.findBookingsByUserId(userId);
            if(CollectionUtils.isEmpty(bookingListOfUser)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            System.out.println(bookingListOfUser.get(0).getId());
            List<BookingDto> responseBookingDtos = bookingConvert.convert(bookingListOfUser);
            return ResponseEntity.status(HttpStatus.OK).body(responseBookingDtos);
        } catch(Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
}