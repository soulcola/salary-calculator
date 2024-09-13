package com.petrtitov.validate;

import com.petrtitov.dto.ByDateWritePaymentDto;
import com.petrtitov.dto.ByDaysWritePaymentDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PaymentDatesValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ByDateWritePaymentDto.class.isAssignableFrom(clazz) || ByDaysWritePaymentDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof ByDateWritePaymentDto) {
            ByDateWritePaymentDto dto = (ByDateWritePaymentDto) target;

            if (dto.getEndDate() != null && dto.getStartDate() != null
                    && dto.getEndDate().isBefore(dto.getStartDate())) {
                errors.rejectValue("endDate", null,
                        "Start date cannot be after end date");
            }
        }
    }
}
