package com.example.whatstocleanbackend.domain.chore;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.domain.chore.periodicity.Periodicity;
import com.example.whatstocleanbackend.domain.date.Date;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
public class PeriodicityMatcherTest {

    private final Date DATE_DAILY = Date.createDate(2020, 01, 01);
    private final Date DATE_TWICE_A_WEEK = Date.createDate(2020, 01, 03);
    private final Date DATE_WEEKLY = Date.createDate(2020, 01, 11);
    private final Date DATE_BIWEEKLY = Date.createDate(2020, 01, 18);
    private final Date DATE_MONTHLY = Date.createDate(2020, 02, 01);
    private final Date DATE_QUARTERLY = Date.createDate(2020, 01, 04);

    @Test
    public void dailyMatcher() {
        assertTrue(Periodicity.DAILY.matches(DATE_DAILY));
        assertTrue(Periodicity.DAILY.matches(DATE_TWICE_A_WEEK));
        assertTrue(Periodicity.DAILY.matches(DATE_WEEKLY));
        assertTrue(Periodicity.DAILY.matches(DATE_BIWEEKLY));
        assertTrue(Periodicity.DAILY.matches(DATE_MONTHLY));
        assertTrue(Periodicity.DAILY.matches(DATE_QUARTERLY));
    }

    @Test
    public void twiceAWeekMatcher() {
        assertFalse(Periodicity.TWICE_A_WEEK.matches(DATE_DAILY));
        assertTrue(Periodicity.TWICE_A_WEEK.matches(DATE_TWICE_A_WEEK));
        assertFalse(Periodicity.TWICE_A_WEEK.matches(DATE_WEEKLY));
        assertFalse(Periodicity.TWICE_A_WEEK.matches(DATE_BIWEEKLY));
        assertFalse(Periodicity.TWICE_A_WEEK.matches(DATE_MONTHLY));
        assertFalse(Periodicity.TWICE_A_WEEK.matches(DATE_QUARTERLY));
    }

    @Test
    public void weeklyMatcher() {
        assertFalse(Periodicity.WEEKLY.matches(DATE_DAILY));
        assertFalse(Periodicity.WEEKLY.matches(DATE_TWICE_A_WEEK));
        assertTrue(Periodicity.WEEKLY.matches(DATE_WEEKLY));
        assertTrue(Periodicity.WEEKLY.matches(DATE_BIWEEKLY));
        assertTrue(Periodicity.WEEKLY.matches(DATE_MONTHLY));
        assertTrue(Periodicity.WEEKLY.matches(DATE_QUARTERLY));
    }

    @Test
    public void biweeklyMatcher() {
        assertFalse(Periodicity.BIWEEKLY.matches(DATE_DAILY));
        assertFalse(Periodicity.BIWEEKLY.matches(DATE_TWICE_A_WEEK));
        assertFalse(Periodicity.BIWEEKLY.matches(DATE_WEEKLY));
        assertTrue(Periodicity.BIWEEKLY.matches(DATE_BIWEEKLY));
        assertTrue(Periodicity.BIWEEKLY.matches(DATE_MONTHLY));
        assertTrue(Periodicity.BIWEEKLY.matches(DATE_QUARTERLY));
    }

    @Test
    public void monthlyMatcher() {
        assertFalse(Periodicity.MONTHLY.matches(DATE_DAILY));
        assertFalse(Periodicity.MONTHLY.matches(DATE_TWICE_A_WEEK));
        assertFalse(Periodicity.MONTHLY.matches(DATE_WEEKLY));
        assertFalse(Periodicity.MONTHLY.matches(DATE_BIWEEKLY));
        assertTrue(Periodicity.MONTHLY.matches(DATE_MONTHLY));
        assertTrue(Periodicity.MONTHLY.matches(DATE_QUARTERLY));
    }

    @Test
    public void quarterlyMatcher() {
        assertFalse(Periodicity.QUARTERLY.matches(DATE_DAILY));
        assertFalse(Periodicity.QUARTERLY.matches(DATE_TWICE_A_WEEK));
        assertFalse(Periodicity.QUARTERLY.matches(DATE_WEEKLY));
        assertFalse(Periodicity.QUARTERLY.matches(DATE_BIWEEKLY));
        assertFalse(Periodicity.QUARTERLY.matches(DATE_MONTHLY));
        assertTrue(Periodicity.QUARTERLY.matches(DATE_QUARTERLY));
    }
}
