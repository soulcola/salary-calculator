package com.petrtitov.service;

import com.petrtitov.model.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static com.petrtitov.util.SalaryUtil.DAYS_AVG;
import static com.petrtitov.util.SalaryUtil.countWorkingDays;

@Service
public class VacationPayService {

    public Payment calculateSalaryByDates(LocalDate startDate, LocalDate endDate, BigDecimal salary) {
        var workingDays = countWorkingDays(startDate, endDate);
        return calculateSalaryByDays(workingDays, salary);
    }

    public Payment calculateSalaryByDays(long days, BigDecimal salary) {
        var vacationPay = salary
                .divide(BigDecimal.valueOf(DAYS_AVG), 20, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(days));
        return new Payment(salary, days, vacationPay.setScale(2, RoundingMode.HALF_UP));
    }
}
