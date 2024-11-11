package com.bookings.repository;
import java.util.List;
import java.util.UUID;

import com.bookings.entity.Show;
import com.bookings.entity.TheaterMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, UUID> {
    List<Show> findByTheaterMovie(TheaterMovie theaterMovie);
}
