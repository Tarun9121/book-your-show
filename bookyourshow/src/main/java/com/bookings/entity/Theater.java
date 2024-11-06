package com.bookings.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="theaters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theater {
    private UUID id;
    private String name;
    private String country;
    private String state;
    private String district;
    private String locality;
    private Integer pincode;
    private Integer noOfSeats;
}
