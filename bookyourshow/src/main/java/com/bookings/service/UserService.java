package com.bookings.service;

import com.bookings.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface UserService {
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto);
    ResponseEntity<String> softDeleteUser(UUID id);
}