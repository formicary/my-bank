package com.abc;

import static java.lang.Math.abs;

public class Utility {

    public final String currencyFormatDollar = "$%,.2f";

    // Format the number as currency based on the supplied currency format
    public String formatAmount(double amount, String currencyFormat) {
        return String.format(currencyFormat, amount);
    }


    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    public String formatWordForPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

}
