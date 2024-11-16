package com.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserRequestDto extends BaseResponseDto {
    private UUID id;
    private String fullName;
    private String email;
    private String mobileNo;
    private boolean deleted = false; // Soft delete flag
    private String password;
}
