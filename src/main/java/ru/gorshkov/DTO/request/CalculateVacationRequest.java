package ru.gorshkov.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CalculateVacationRequest {
    @NotNull(message = "Average salary is required")
    @DecimalMin(value = "0.01", message = "Average salary must be greater than zero")
    private BigDecimal averageSalary;

    @Min(value = 1, message = "Vacation days must be at least 1")
    private Integer vacationDays;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startVacation;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endVacation;


    @AssertTrue(message = "Start date of vacation must be before its end date")
    public boolean isDateOrderValid() {
        if (startVacation == null || endVacation == null) {
            return true;
        }
        return startVacation.isBefore(endVacation);
    }

    @AssertTrue(message = "choose one of the options: vacationDays or startVacation and endVacation")
    public boolean isChosenOneCalcMethod() {
        if ((startVacation == null & endVacation == null) & vacationDays != null) {
            return true;
        }
        return (startVacation != null & endVacation != null) & vacationDays == null;
    }
}
