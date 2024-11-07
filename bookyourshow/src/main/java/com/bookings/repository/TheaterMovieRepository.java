package com.bookings.repository;
import java.util.UUID;

import com.bookings.entity.TheaterMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterMovieRepository extends JpaRepository<TheaterMovie, UUID> {
    // Add custom query methods here if needed
}
