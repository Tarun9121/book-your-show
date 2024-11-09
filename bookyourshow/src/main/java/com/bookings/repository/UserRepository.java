package com.bookings.repository;

import java.util.UUID;

import com.bookings.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Add custom query methods here if needed
    boolean existsByEmailAndMobileNo(String email, String mobileNo);
}