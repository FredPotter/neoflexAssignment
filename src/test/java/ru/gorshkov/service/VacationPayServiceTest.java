package ru.gorshkov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gorshkov.DTO.request.CalculateVacationRequest;
import ru.gorshkov.gateway.CalendarService;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
class VacationPayServiceTest {

    @Autowired
    private VacationPayService vacationPayService;

    @MockBean
    private CalendarService calendarService;

    @Test
    @DisplayName("Test calculateVacationPayByPeriod Success")
    void calculateVacationPayByPeriod() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        doReturn(objectMapper.readTree("{\n" +
                "    \"status\": \"ok\",\n" +
                "    \"statistic\": {\n" +
                "        \"calendar_days_without_holidays\": 2\n" +
                "    }\n" +
                "}")).when(calendarService).getCalendarDataJson(LocalDate.of(2024, 5, 8),
                LocalDate.of(2024, 5, 10));
        CalculateVacationRequest mockRequest = new CalculateVacationRequest(BigDecimal.valueOf(20000), null,
                LocalDate.of(2024, 5, 8), LocalDate.of(2024, 5, 10));
        // Act
        BigDecimal answer = vacationPayService.calculateVacationPayByPeriod(mockRequest);
        // Assert
        assert answer.equals(BigDecimal.valueOf(1365.18));
    }

    @Test
    @DisplayName("Test calculateVacationPayByDays Success")
    void calculateVacationPayByDays() {
        // Arrange
        CalculateVacationRequest mockRequest = new CalculateVacationRequest(BigDecimal.valueOf(20000), 3,
                null, null);
        // Act
        BigDecimal answer = vacationPayService.calculateVacationPayByDays(mockRequest);
        // Assert
        assert answer.equals(BigDecimal.valueOf(2047.77));
    }
}