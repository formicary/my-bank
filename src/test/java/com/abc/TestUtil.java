package com.abc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtil {

    public LocalDate generateDate(String dateInString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return LocalDate.parse(dateInString, formatter);
    }
}
