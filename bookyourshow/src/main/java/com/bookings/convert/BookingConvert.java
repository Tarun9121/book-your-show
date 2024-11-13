package com.bookings.convert;

import com.bookings.dto.BookingDto;
import com.bookings.dto.BookingResponseDto;
import com.bookings.entity.Booking;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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

    public List<BookingDto> convert(List<Booking> bookingList) {
        List<BookingDto> responseDtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(bookingList)) {
            bookingList.forEach(booking -> {
                responseDtoList.add(convert(booking));
            });
        }
        return responseDtoList;
    }
}
