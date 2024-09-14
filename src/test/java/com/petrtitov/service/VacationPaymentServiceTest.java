package com.petrtitov.service;

import com.petrtitov.model.Payment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.petrtitov.service.PaymentTestData.*;

@SpringBootTest
class VacationPaymentServiceTest {

    @Autowired
    private VacationPayService service;

    @Test
    void calculateSalaryByDates() {
        Payment actual = service.calculateSalaryByDays(testDuration1, BigDecimal.valueOf(testSalary));
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(testPayment1);
    }

    @Test
    void calculateSalaryByDays() {
        Payment actual = service.calculateSalaryByDates(startDate, endDate, BigDecimal.valueOf(testSalary));
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(testPayment2);
    }
}