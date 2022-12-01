package com.example.whatstocleanbackend.domain.chore.periodicity;

import com.example.whatstocleanbackend.domain.date.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.function.BiPredicate;

@AllArgsConstructor
public enum Periodicity {
    DAILY("Every day", PeriodicityMatcher.matcherDaily),
    TWICE_A_WEEK("Every Tuesday and Friday", PeriodicityMatcher.matcherTwiceAWeek),
    WEEKLY("Every weekend", PeriodicityMatcher.matcherWeekly),
    BIWEEKLY("Every two weekends", PeriodicityMatcher.matcherBiweekly),
    MONTHLY("Every first weekend of month", PeriodicityMatcher.matcherMonthly),
    QUARTERLY("Every first weekend of quarter", PeriodicityMatcher.matcherQuarterly);

    @Getter
    String meaning;

    private BiPredicate<Periodicity, LocalDate> matcher;

    public Boolean matches(Date date) {
        LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        return matcher.test(this, localDate);
    }
}
