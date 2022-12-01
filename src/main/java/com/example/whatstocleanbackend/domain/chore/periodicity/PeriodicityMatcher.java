package com.example.whatstocleanbackend.domain.chore.periodicity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.BiPredicate;

public class PeriodicityMatcher {

    public static BiPredicate<Periodicity, LocalDate> matcherDaily = (periodicity, localDate) -> {
        return true;
    };

    public static BiPredicate<Periodicity, LocalDate> matcherTwiceAWeek = (periodicity, localDate) -> {
        return Set.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY).contains(localDate.getDayOfWeek());
    };

    public static BiPredicate<Periodicity, LocalDate> matcherWeekly = (periodicity, localDate) -> {
        return isWeekend(localDate);
    };

    public static BiPredicate<Periodicity, LocalDate> matcherBiweekly = (periodicity, localDate) -> {

        Boolean weekendCondition = isWeekend(localDate);
        Boolean firstWeekCondition = localDate.minusWeeks(1).getMonthValue() != localDate.getMonthValue();
        Boolean thirdWeekCondition =
                (localDate.minusWeeks(2).getMonthValue() == localDate.getMonthValue()) && (localDate.minusWeeks(3).getMonthValue() != localDate.getMonthValue());

        return weekendCondition && (firstWeekCondition || thirdWeekCondition);
    };

    public static BiPredicate<Periodicity, LocalDate> matcherMonthly = (periodicity, localDate) -> {
        Boolean weekendCondition = isWeekend(localDate);
        Boolean firstWeekCondition = localDate.minusWeeks(1).getMonthValue() != localDate.getMonthValue();
        return weekendCondition && firstWeekCondition;
    };

    public static BiPredicate<Periodicity, LocalDate> matcherQuarterly = (periodicity, localDate) -> {
        Boolean monthCondition = matcherMonthly.test(periodicity, localDate);
        Integer currentQuarter = (localDate.getMonthValue()-1)/3;
        Integer previousQuarter = (localDate.minusWeeks(1).getMonthValue()-1)/3;
        Boolean firstWeekCondition = currentQuarter != previousQuarter;
        return monthCondition && firstWeekCondition;
    };

    private static Boolean isWeekend(LocalDate localDate) {
        return Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(localDate.getDayOfWeek());
    }
}
