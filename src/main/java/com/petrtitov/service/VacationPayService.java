package com.petrtitov.service;

import com.petrtitov.model.Payment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.petrtitov.util.SalaryUtil.DAYS_AVG;
import static com.petrtitov.util.SalaryUtil.countWorkingDays;

@Service
public class VacationPayService {

    public Payment calculateSalaryByDates(LocalDate startDate, LocalDate endDate, double salary) {
        var workingDays = countWorkingDays(startDate, endDate);
        return calculateSalaryByDays(workingDays, salary);
    }

    public Payment calculateSalaryByDays(long days, double salary) {
        var vacationPay = salary / DAYS_AVG * days;
        return new Payment(salary, days, vacationPay);
    }
}
