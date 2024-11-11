package com.bookings.dto;

import com.bookings.entity.TheaterMovie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class TheaterDto extends BaseResponseDto {
    private UUID id;
    private String name;
    private Integer noOfSeats;
    private String country;
    private String state;
    private String district;
    private String locality;
    private Integer pincode;
}
