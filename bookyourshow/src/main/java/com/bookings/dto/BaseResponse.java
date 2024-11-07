package com.bookings.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Private constructor to enforce usage of factory methods
    private BaseResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> BaseResponse<T> success(HttpStatus status, String message, T data) {
        return new BaseResponse<>(status, message, data);
    }

    public static <T> BaseResponse<T> success(HttpStatus status, String message) {
        return new BaseResponse<>(status, message, null);
    }

    public static <T> BaseResponse<T> error(HttpStatus status, String message) {
        return new BaseResponse<>(status, message, null);
    }
}

