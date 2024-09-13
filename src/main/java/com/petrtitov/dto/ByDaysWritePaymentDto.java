package com.petrtitov.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ByDaysWritePaymentDto {

    @Positive
    private double salary;

    @Positive
    private long vacationDays;
}
