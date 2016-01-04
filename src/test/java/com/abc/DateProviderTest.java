package com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateProviderTest {
  SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

  @Test
  public void correctlyReturnsDayDifference() throws ParseException {
    Date d1 = myFormat.parse("04/01/2016 11:46:08");
    Date d2 = myFormat.parse("03/01/2016 11:46:08");
    assertTrue(DateProvider.getDayDifference(d2, d1) == 1);
    d2 = myFormat.parse("03/01/2016 11:46:09");
    assertTrue(DateProvider.getDayDifference(d2, d1) == 0);
  }
  
  @Test
  public void correctlyAccountsForLeapYears() throws ParseException {
    Date d1 = myFormat.parse("01/03/2016 00:00:00");
    Date d2 = myFormat.parse("28/02/2016 00:00:00");
    assertTrue(DateProvider.getDayDifference(d2, d1) == 2);  
    d1 = myFormat.parse("01/03/2015 00:00:00");
    d2 = myFormat.parse("28/02/2015 00:00:00");
    assertTrue(DateProvider.getDayDifference(d2, d1) == 1);    
  }
}