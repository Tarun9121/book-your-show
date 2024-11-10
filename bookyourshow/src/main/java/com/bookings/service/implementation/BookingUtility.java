package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.entity.Booking;
import com.bookings.exception.ApiException;
import com.bookings.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class BookingUtility {
    public final BookingRepository bookingRepository;

    @Autowired
    public BookingUtility(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Integer seatsBooked(UUID showId) {
        try {
            if(showId == null || showId.equals(new UUID(0, 0))) {
                throw new ApiException(BookYourShow.DATA_NULL);
            }

            List<Booking> bookingList = bookingRepository.findByShowId(showId);
            if(!CollectionUtils.isEmpty(bookingList)) {
                Integer totalSeats = bookingList.stream()
                        .filter(booking -> booking.getNoOfSeatsSelected() != null)
                        .mapToInt(Booking::getNoOfSeatsSelected)
                        .sum();
                return totalSeats;
            }
            return 0;
        }
        catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
}
