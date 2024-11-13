package com.bookings.controller;

import com.bookings.dto.UserDto;
import com.bookings.service.implementation.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        return  userServiceImpl.registerUser(userDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") UUID userId) {
        return userServiceImpl.getUserDetailsById(userId);
    }

    @Operation(summary = "Soft Delete User", description = "Soft delete a user by setting the isDeleted flag")
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteUser(@PathVariable UUID id) {
        return userServiceImpl.softDeleteUser(id);
    }

    @GetMapping("/email/{emailId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("emailId") String emailId) {
        return userServiceImpl.getUserByEmail(emailId);
    }

}