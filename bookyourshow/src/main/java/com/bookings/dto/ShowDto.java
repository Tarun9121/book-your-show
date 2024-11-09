package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class ShowDto extends BaseResponseDto {
    private UUID id;
    private UUID theaterId;
    private UUID movieId;
    private LocalDate showDate;
    private LocalTime showTime;
}
