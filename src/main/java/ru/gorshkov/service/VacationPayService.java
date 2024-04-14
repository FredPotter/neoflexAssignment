package ru.gorshkov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gorshkov.DTO.request.CalculateVacationRequest;
import ru.gorshkov.gateway.CalendarService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class VacationPayService {
    private final CalendarService calendarService;

    public BigDecimal calculateVacationPayByPeriod(CalculateVacationRequest request) throws JsonProcessingException {
        JsonNode rootNode = calendarService.getCalendarDataJson(request.getStartVacation(), request.getEndVacation());
        String calendarDaysWithoutHolidays = rootNode.path("statistic").path("calendar_days_without_holidays").asText();
        // Условно возьмем что зарплата за день равна зарплате за месяц деленной на 29.3
        BigDecimal salaryPerDay = request.getAverageSalary().divide(new BigDecimal("29.3"), 2, RoundingMode.HALF_UP);
        return salaryPerDay.multiply(new BigDecimal(calendarDaysWithoutHolidays));
    }
    public BigDecimal calculateVacationPayByDays(CalculateVacationRequest request) {
        // Условно возьмем что зарплата за день равна зарплате за месяц деленной на 29.3
        BigDecimal salaryPerDay = request.getAverageSalary().divide(new BigDecimal("29.3"), 2, RoundingMode.HALF_UP);
        return salaryPerDay.multiply(new BigDecimal(request.getVacationDays()));
    }
}

