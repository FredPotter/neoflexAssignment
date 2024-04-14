package ru.gorshkov.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BindExceptionResponse {
    private String message;
}
