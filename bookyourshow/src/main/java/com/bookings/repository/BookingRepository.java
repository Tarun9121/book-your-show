package com.bookings.repository;
import java.util.UUID;

import com.bookings.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    // Add custom query methods here if needed
}
