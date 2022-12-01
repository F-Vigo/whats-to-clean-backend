package com.example.whatstocleanbackend.service.validator;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.domain.date.DateParams;
import com.example.whatstocleanbackend.service.validation.validator.DateValidator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
public class DateValidatorTest {

    private DateValidator dateValidator = new DateValidator();

    @ParameterizedTest
    @CsvSource({
            "2020,01,01,true",
            "2020,00,01,false",
            "2020,13,01,false",
            "2020,01,00,false",
            "2020,01,32,false",
            "2020,02,30,false"
    })
    public void validatorTest(int year, int month, int day, boolean expected) {
        DateParams dateParams = new DateParams(year, month, day);
        assertEquals(expected, dateValidator.isValid(dateParams, null));
    }
}
