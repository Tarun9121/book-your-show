package com.bookings.repository;

import com.bookings.entity.Casting;
import com.bookings.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CastingRepository extends JpaRepository<Casting, UUID> {
    List<Casting> findByMovie(Movie movie);
}
