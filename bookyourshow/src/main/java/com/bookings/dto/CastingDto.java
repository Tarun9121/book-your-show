package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class CastingDto {
    private UUID movieId;
    private UUID actorId;
    private String characterName;
    private String role;
}
