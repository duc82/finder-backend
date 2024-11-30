package com.duc82.finderapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data // Lombok's annotation to generate getters and setters
@AllArgsConstructor // Lombok's annotation to generate a constructor with all arguments
@Builder // Lombok's annotation to generate a builder pattern
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}