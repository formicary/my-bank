package com.abc;

import com.abc.helper.DateProvider;
import org.junit.Test;
import java.time.LocalDate;
import java.util.Date;


import static org.junit.Assert.assertEquals;

public class DateTest {

    @Test
    public void customerSummary() {
        assertEquals(LocalDate.now().isEqual(LocalDate.now()), true);
    }

    @Test
    public void offsetTest() {
        System.out.println(DateProvider.now());
        System.out.println(DateProvider.offset(10));
    }

    @Test
    public void chronologyTest() {
        try {
            for (Date date : DateProvider.buildChronology(40)) {
                System.out.println(date);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
