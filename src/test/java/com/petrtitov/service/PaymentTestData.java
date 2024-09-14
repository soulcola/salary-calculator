package com.petrtitov.service;

import com.petrtitov.model.Payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class PaymentTestData {

    public static final LocalDate startDate = LocalDate.of(2024, 1, 6);
    public static final LocalDate endDate = LocalDate.of(2024, 1, 18);
    public static final long testDuration1 = 13L;
    public static final long testDuration2 = 10L;
    public static final double testSalary = 100000.0;
    public static final BigDecimal vacationPayment1 = new BigDecimal("44368.60068259386").setScale(2, RoundingMode.HALF_UP);
    public static final BigDecimal vacationPayment2 = new BigDecimal("34129.69283276451").setScale(2, RoundingMode.HALF_UP);


    public static final Payment testPayment1 = new Payment(BigDecimal.valueOf(testSalary), testDuration1, vacationPayment1);
    public static final Payment testPayment2 = new Payment(BigDecimal.valueOf(testSalary), testDuration2, vacationPayment2);
}
