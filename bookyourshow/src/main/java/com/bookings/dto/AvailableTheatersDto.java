package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class AvailableTheatersDto {
    private TheaterDto theater;
    private List<AvailableShowDto> availableShows = new ArrayList<>();
}
