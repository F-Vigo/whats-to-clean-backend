package com.example.whatstocleanbackend.service.validation.validator;

import com.example.whatstocleanbackend.domain.date.DateParams;
import com.example.whatstocleanbackend.service.validation.constraint.DateConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class DateValidator implements ConstraintValidator<DateConstraint, DateParams> {

    private static Integer totalDays(Integer year, Integer month) {

        Boolean isLapYear = (year%4== 0 && year%100 != 0) || (year%400 == 0);
        Integer result = 0;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                result = 31;
                break;
            case 2:
                result = isLapYear ? 29 : 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                result = 30;
                break;
        }
        return result;
    }

    @Override
    public boolean isValid(DateParams dateParams, ConstraintValidatorContext constraintValidatorContext) {
        Boolean yearOk = 0 < dateParams.getYear();
        Boolean monthOk = 1 <= dateParams.getMonth() && dateParams.getMonth() <= 12;
        Boolean dayOk = 1 <= dateParams.getDay() && dateParams.getDay() <= totalDays(dateParams.getYear(), dateParams.getMonth());
        return yearOk && monthOk && dayOk;
    }
}
