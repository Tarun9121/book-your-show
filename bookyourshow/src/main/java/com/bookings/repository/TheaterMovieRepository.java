package com.bookings.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bookings.dto.TheaterMovieNativeDto;
import com.bookings.entity.Movie;
import com.bookings.entity.Theater;
import com.bookings.entity.TheaterMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterMovieRepository extends JpaRepository<TheaterMovie, UUID> {
    Optional<TheaterMovie> findByTheaterAndMovie(Theater theater, Movie movie);

    @Query(nativeQuery = true, value = "select * from theater_movie where movie_id = :movieId")
    List<TheaterMovieNativeDto> getAssociatedTheaters01(UUID movieId);

    @Query("SELECT tm FROM TheaterMovie tm WHERE tm.movie.id = :movieId")
    List<TheaterMovie> getAssociatedTheaters(UUID movieId);

    List<TheaterMovie> findByMovie(Movie movie);
}