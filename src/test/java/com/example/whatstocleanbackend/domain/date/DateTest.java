package com.example.whatstocleanbackend.domain.date;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.domain.date.Date;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
public class DateTest {

    @Test
    public void whenDateTest_withValidDate_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> Date.createDate(2020, 01, 01));
    }

    @Test
    public void whenDateTest_withInvalidDate_thenNoExceptionThrown() {
        assertThrows(RuntimeException.class, () -> Date.createDate(2020, 01, 00));
    }
}
