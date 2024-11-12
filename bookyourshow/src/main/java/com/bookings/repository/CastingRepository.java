package com.bookings.repository;

import com.bookings.entity.Casting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CastingRepository extends JpaRepository<Casting, UUID> {

}
