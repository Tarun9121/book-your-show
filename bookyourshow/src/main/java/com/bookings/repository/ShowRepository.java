package com.bookings.repository;
import java.util.UUID;

import com.bookings.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, UUID> {
    // Add custom query methods here if needed
}
