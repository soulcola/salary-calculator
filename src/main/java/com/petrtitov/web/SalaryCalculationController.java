package com.petrtitov.web;

import com.petrtitov.model.Payment;
import com.petrtitov.service.VacationPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vacation pay controller")
@RequiredArgsConstructor
@Validated
public class SalaryCalculationController {

    static final String REST_URL = "/api/calculate-salary";
    private final VacationPayService service;

    @Operation(summary = "Calculate vacation pay by vacation days and average monthly salary")
    @GetMapping(REST_URL)
    public Payment getVacationPay(@RequestParam @Positive long days,
                                  @RequestParam @Positive @NotNull BigDecimal salary) {
        log.info("Calculate vacation pay: {} days, average monthly salary: {}", days, salary);
        return service.calculateSalaryByDays(days, salary);
    }

    @Operation(summary = "Calculate vacation pay by start date and end date and average monthly salary")
    @GetMapping(REST_URL + "/by-dates")
    public Payment getVacationPayByDates(@RequestParam @NotNull LocalDate startDate,
                                         @RequestParam @NotNull LocalDate endDate,
                                         @RequestParam @Positive @NotNull BigDecimal salary) {
        log.info("Calculate vacation pay: start date: {}, end date: {} monthly salary: {}", startDate, endDate, salary);
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date is after end date");
        }
        return service.calculateSalaryByDates(startDate, endDate, salary);
    }
}
