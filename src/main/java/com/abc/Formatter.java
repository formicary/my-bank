package com.abc;

import static java.lang.Math.abs;

/**
 *
 * @author R. Fei
 */
public class Formatter {
    /* Make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end 
     * 1 account, 2 accounts, 3 accounts, etc... */
    String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
    //Formatting 1200.5 to $1,200.50
    String toDollars(double amount){
        return String.format("$%,.2f", abs(amount));
    }
}
