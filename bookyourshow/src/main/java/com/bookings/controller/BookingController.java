package com.bookings.controller;

import com.bookings.dto.BookingDto;
import com.bookings.dto.BookingRequestDto;
import com.bookings.dto.BookingResponseDto;
import com.bookings.service.BookingService;
import com.bookings.service.implementation.BookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
@Tag(name = "Booking", description = "APIs for Managing Bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create Booking", description = "Book seats for a specific show")
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return bookingService.createBooking(bookingRequestDto);
    }

    @Operation(summary = "Get Booking by ID", description = "Retrieve booking details by booking ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable UUID id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/get-bookings/{userId}")
    public ResponseEntity<List<BookingDto>> getBookingsByUserId(@PathVariable("userId") UUID userId) {
        return bookingService.getBookingsByUserId(userId);
    }

}