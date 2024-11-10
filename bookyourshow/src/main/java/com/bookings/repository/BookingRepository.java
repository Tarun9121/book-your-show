package com.bookings.repository;
import java.util.List;
import java.util.UUID;

import com.bookings.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    @Query("SELECT b FROM Booking b WHERE b.show.id = :showId")
    List<Booking> findByShowId(@Param("showId") UUID showId);
}
