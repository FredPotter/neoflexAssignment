package ru.gorshkov.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.BindException;
import ru.gorshkov.DTO.response.BindExceptionResponse;

import java.util.ArrayList;

@ControllerAdvice
public class BindExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<BindExceptionResponse> handleValidationExceptions(BindException ex) {
        ArrayList<BindExceptionResponse> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(new BindExceptionResponse(errorMessage));
        });
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new BindExceptionResponse(errors.toString()));
    }
}
