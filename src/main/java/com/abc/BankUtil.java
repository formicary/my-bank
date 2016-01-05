package com.abc;

import static java.lang.Math.abs;

public class BankUtil {

  /*
   * Make sure correct plural of word is created based on the number passed in:
   * If number passed in is less than 1 just return the word otherwise add an
   * 's' at the end, requires number to be positive
   */
  public static String format(int number, String word) {
    assert number >= 0 : "Format requires number to be positive but got " + number;
    return number + " " + (number == 1 ? word : word + "s");
  }
  
  public static String toDollars(double d) {
    //does not account for negative d
    return String.format("$%,.2f", abs(d));
  }
  
}
