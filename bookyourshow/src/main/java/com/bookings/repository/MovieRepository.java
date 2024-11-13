package com.bookings.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bookings.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    @Query("SELECT m FROM Movie m WHERE m.deleted = false")
    List<Movie> findAllActiveMovie();

    @Query(nativeQuery = true, value = "SELECT * FROM movies ORDER BY release_date DESC")
    List<Movie> findAllLatestMovies();

    @Query("SELECT m FROM Movie m WHERE m.id = :id AND m.deleted = false")
    Optional<Movie> findActiveById(UUID id);

    @Query(nativeQuery = true, value= "select * from movies where name like %:name%")
    List<Movie> searchMovie(@Param("name") String name);
}
