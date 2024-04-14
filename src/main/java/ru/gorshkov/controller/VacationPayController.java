package ru.gorshkov.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gorshkov.DTO.request.CalculateVacationRequest;
import ru.gorshkov.DTO.response.CalculationResponse;
import ru.gorshkov.gateway.CalendarService;
import ru.gorshkov.service.VacationPayService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;

@RestController
@RequiredArgsConstructor
public class VacationPayController {
    private final VacationPayService vacationPayService;
    private final CalendarService calendarService;

    @GetMapping("/calculate")
    public ResponseEntity<?> calculateVacationPay(@Valid CalculateVacationRequest request) {
        if(request.getVacationDays() != null) {
            BigDecimal result = vacationPayService.calculateVacationPayByDays(request);
            return ResponseEntity.ok(new CalculationResponse(result));
        }
        try {
            BigDecimal result = vacationPayService.calculateVacationPayByPeriod(request);
            return ResponseEntity.ok(new CalculationResponse(result));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }
}
