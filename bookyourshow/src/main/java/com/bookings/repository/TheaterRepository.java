package com.bookings.repository;
import java.util.UUID;

import com.bookings.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, UUID> {
    // Add custom query methods here if needed
}
