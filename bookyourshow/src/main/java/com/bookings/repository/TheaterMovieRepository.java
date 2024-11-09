package com.bookings.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bookings.entity.Movie;
import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterMovieRepository extends JpaRepository<TheaterMovie, UUID> {
    Optional<TheaterMovie> findByTheaterAndMovie(Theater theater, Movie movie);
    List<TheaterMovie> findByMovieId(UUID movieId);
//    UUID findIdByTheaterId(UUID theaterId, UUID movieId);
}