package com.petrtitov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private BigDecimal salary;
    private long vacationDays;
    private BigDecimal vacationPayment;
}