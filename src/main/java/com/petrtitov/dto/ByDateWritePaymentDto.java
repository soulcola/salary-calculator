package com.petrtitov.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ByDateWritePaymentDto {

    @Positive
    private double salary;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
