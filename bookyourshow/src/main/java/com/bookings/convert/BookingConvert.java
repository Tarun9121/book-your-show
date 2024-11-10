package com.bookings.convert;

import com.bookings.dto.BookingDto;
import com.bookings.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingConvert {
    public BookingDto convert(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getUser().getId(),
                booking.getShow().getId(),
                booking.getNoOfSeatsSelected()
        );
    }
}
