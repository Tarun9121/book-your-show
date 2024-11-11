package com.bookings.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bookings.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Add custom query methods here if needed
    boolean existsByEmailAndMobileNo(String email, String mobileNo);

    @Query("SELECT u FROM User u WHERE u.deleted = false")
    List<User> findAllActiveUsers();

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deleted = false")
    Optional<User> findActiveById(UUID id);
}