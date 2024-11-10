package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto extends BaseResponseDto {
    private UUID id;
    private UUID userId;
    private UUID showId;
    private Integer noOfSeatsSelected;
}