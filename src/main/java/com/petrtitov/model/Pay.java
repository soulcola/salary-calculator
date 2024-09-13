package com.petrtitov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pay {
    private double salary;
    private long vacationDays;
    private double payment;
}