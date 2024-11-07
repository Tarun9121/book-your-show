package com.bookings.dto;

import com.bookings.entity.TheaterMovie;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

public class TheaterDto {
    private UUID id;
    private String name;
    private Integer noOfSeats;
    private String country;
    private String state;
    private String district;
    private String locality;
    private Integer pincode;
}
