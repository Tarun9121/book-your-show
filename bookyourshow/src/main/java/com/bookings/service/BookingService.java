package com.bookings.service;

import com.bookings.dto.BookingDto;
import com.bookings.dto.BookingRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BookingService {

    ResponseEntity<String> createBooking(BookingRequestDto bookingRequestDto);
    ResponseEntity<BookingDto> getBookingById(UUID id);

}