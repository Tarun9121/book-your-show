package com.bookings.service;

import com.bookings.dto.BookingDetails;
import com.bookings.dto.BookingDto;
import com.bookings.dto.BookingRequestDto;
import com.bookings.dto.BookingResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface BookingService {
    ResponseEntity<List<BookingDto>> getBookingsByUserId(UUID userId);
    ResponseEntity<BookingDto> createBooking(BookingRequestDto bookingRequestDto);
    ResponseEntity<BookingDto> getBookingById(UUID id);
    ResponseEntity<BookingDetails> getBookingDetails(UUID bookingId);

}