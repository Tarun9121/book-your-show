package com.bookings.repository;
import java.util.List;
import java.util.UUID;

import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, UUID> {
    List<TheaterMovie> findRegisteredMoviesById(UUID theaterId);
}
