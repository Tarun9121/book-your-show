package com.bookings.repository;

import com.bookings.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<Actor, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM actors WHERE name = :name AND date_of_birth = :dateOfBirth")
    Actor findByNameAndDOB(String name, LocalDate dateOfBirth);
}
