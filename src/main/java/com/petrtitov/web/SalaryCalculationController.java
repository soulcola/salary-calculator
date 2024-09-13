package com.petrtitov.web;

import com.petrtitov.dto.ByDateWritePaymentDto;
import com.petrtitov.dto.ByDaysWritePaymentDto;
import com.petrtitov.model.Pay;
import com.petrtitov.service.VacationPayService;
import com.petrtitov.validate.PaymentDatesValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vacation pay Controller")
@RequiredArgsConstructor
public class SalaryCalculationController {

    private final PaymentDatesValidator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    static final String REST_URL = "/api/calculate-salary";
    private final VacationPayService service;

    @Operation(summary = "Calculate vacation pay by vacation days and average monthly salary")
    @PostMapping(REST_URL)
    public Pay getVacationPay(@RequestBody @Valid ByDaysWritePaymentDto payment) {
        log.info("Calculate vacation pay: {} days, average monthly salary: {}", payment.getVacationDays(), payment.getSalary());
        return service.calculateSalaryByDays(payment);
    }

    @Operation(summary = "Calculate vacation pay by start date and end date and average monthly salary")
    @PostMapping(REST_URL + "/by-dates")
    public Pay getVacationPayByDates(@RequestBody @Valid ByDateWritePaymentDto payment) {
        log.info("Calculate vacation pay: start date: {}, end date: {} monthly salary: {}", payment.getStartDate(), payment.getEndDate(), payment.getSalary());
        return service.calculateSalaryByDates(payment);
    }
}
