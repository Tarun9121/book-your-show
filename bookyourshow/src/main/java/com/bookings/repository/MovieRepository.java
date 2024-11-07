package com.bookings.repository;
import java.util.UUID;

import com.bookings.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    // Add custom query methods here if needed
}
