package com.petrtitov.service;

import com.petrtitov.dto.ByDateWritePaymentDto;
import com.petrtitov.dto.ByDaysWritePaymentDto;
import com.petrtitov.model.Pay;
import org.springframework.stereotype.Service;

import static com.petrtitov.util.SalaryUtil.DAYS_AVG;
import static com.petrtitov.util.SalaryUtil.countWorkingDays;

@Service
public class VacationPayService {

    public Pay calculateSalaryByDates(ByDateWritePaymentDto payment) {
        var workingDays = countWorkingDays(payment.getStartDate(), payment.getEndDate());
        var vacationPay = payment.getSalary() / DAYS_AVG * workingDays;
        return new Pay(payment.getSalary(), workingDays, vacationPay);
    }

    public Pay calculateSalaryByDays(ByDaysWritePaymentDto payment) {
        var vacationPay = payment.getSalary() / DAYS_AVG * payment.getVacationDays();
        return new Pay(payment.getSalary(), payment.getVacationDays(), vacationPay);
    }
}
