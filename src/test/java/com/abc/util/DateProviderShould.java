package com.abc.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateProviderShould {

    @Test
    public void ReturnCurrentDate() {
        Date expectedDate = Calendar.getInstance().getTime();

        DateProvider dateProvider = DateProvider.getInstance();

        assertThat(expectedDate, sameDate(dateProvider.getCurrentDate()));
    }

    private Matcher<Date> sameDate(final Date comparisonDate) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");

        return new BaseMatcher<Date>() {
            protected Object date  = comparisonDate;

            public boolean matches(Object expected) {
                return formatter.format(date).equals(formatter.format(comparisonDate));
            }

            public void describeTo(Description description) {
                description.appendText(comparisonDate.toString());
            }
        };
    }
}