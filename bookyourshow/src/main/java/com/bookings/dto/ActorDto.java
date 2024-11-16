package com.bookings.dto;

import com.bookings.role.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class ActorDto extends BaseResponseDto {
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private String nationality;
    private String biography;
    private Gender gender;
    private Year debutYear;
}
