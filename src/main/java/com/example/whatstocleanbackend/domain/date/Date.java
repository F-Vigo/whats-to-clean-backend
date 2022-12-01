package com.example.whatstocleanbackend.domain.date;

import com.example.whatstocleanbackend.exception.DateException;
import com.example.whatstocleanbackend.service.validation.validator.DateValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Date implements Comparable<Date> {

    Integer year;
    Integer month;
    Integer day;

    private static DateValidator validator = new DateValidator();

    public static Date createDate(Integer year, Integer month, Integer day) {
        return createDateAux(new DateParams(year, month, day));
    }

    private static Date createDateAux(DateParams dateParams) {
        if (validator.isValid(dateParams, null)) {
            return new Date(dateParams.getYear(), dateParams.getMonth(), dateParams.getDay());
        } else {
            throw new DateException();
        }
    }


    @Override
    public int compareTo(Date that) {
        if (year != that.getYear())
            return year.compareTo(that.getYear());
        if (month != that.getMonth())
            return month.compareTo(that.getMonth());
        return day.compareTo(that.getDay());
    }

}
