package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;

import com.bookings.dto.BookingRequestDto;
import com.bookings.entity.Booking;
import com.bookings.entity.Show;
import com.bookings.entity.User;
import com.bookings.exception.ApiException;
import com.bookings.repository.BookingRepository;
import com.bookings.repository.ShowRepository;
import com.bookings.repository.UserRepository;
import com.bookings.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final Convert convert;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,UserRepository userRepository,ShowRepository showRepository,Convert convert) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.convert = convert;
    }


    @Override
    public ResponseEntity<String> createBooking(BookingRequestDto bookingRequestDto) {
        try {
            // Validate User
            User user = userRepository.findById(bookingRequestDto.getUserId())
                    .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": User ID " + bookingRequestDto.getUserId()));

            // Validate Show
            Show show = showRepository.findById(bookingRequestDto.getShowId())
                    .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": Show ID " + bookingRequestDto.getShowId()));

            // Create and save Booking
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setShow(show);
            booking.setNoOfSeatsSelected(bookingRequestDto.getNoOfSeatsSelected());

            bookingRepository.save(booking);
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
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": Booking ID " + id));

            // Convert to BookingDto using Convert class
            BookingDto bookingDto = convert.convert(booking);
            return ResponseEntity.ok(bookingDto);

        } catch (ApiException e) {
            log.error(BookYourShow.DATA_NOT_FOUND + ": {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error(BookYourShow.SOMETHING_WENT_WRONG + ": {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}