package com.bookings.controller;

import com.bookings.dto.UserDto;
import com.bookings.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        return  userServiceImpl.registerUser(userDto);
    }

//    @GetMapping("/showUser")
//    public  BaseResponse<String> showUser(@RequestBody UserDto userDto){
//
//    }

}