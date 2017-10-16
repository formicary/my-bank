package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
  private static DateProvider instance = null;

  private DateProvider() {
  }
  
  /**
   * Getter for singleton instance of class. 
   * @return instance of DateProvider
   */
  public static DateProvider getInstance() {
    if (instance == null) {
      instance = new DateProvider();
    }
    return instance;
  }

  public Date now() {
    return Calendar.getInstance().getTime();
  }
}
