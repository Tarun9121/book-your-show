package com.bookings.repository;

import com.bookings.entity.MovieCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieCastRepository extends JpaRepository<MovieCast, UUID> {
}
