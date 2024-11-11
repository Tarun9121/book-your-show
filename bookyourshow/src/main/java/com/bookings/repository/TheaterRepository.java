package com.bookings.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, UUID> {
    List<TheaterMovie> findRegisteredMoviesById(UUID theaterId);
    @Query("SELECT t FROM Theater t WHERE t.deleted = false")
    List<Theater> findAllActiveTheater();

    @Query("SELECT t FROM Theater t WHERE t.id = :id AND t.deleted = false")
    Optional<Theater> findActiveById(UUID id);
}
