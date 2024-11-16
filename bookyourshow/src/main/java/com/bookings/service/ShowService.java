package com.bookings.service;

import com.bookings.dto.AvailableTheatersDto;
import com.bookings.dto.ShowDetails;
import com.bookings.dto.ShowRequestDto;
import com.bookings.dto.ShowDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ShowService {
    ResponseEntity<List<ShowDetails>> createShow(UUID theaterId, UUID movieId, List<ShowRequestDto> showRequestDto);
    ResponseEntity<ShowDto> getShowResponseById(UUID id);
    ResponseEntity<String> deleteShow(UUID id);
    List<AvailableTheatersDto> availableShows(UUID movieId);
    public ResponseEntity<ShowDetails> getShowDetailsById(UUID showId);
}