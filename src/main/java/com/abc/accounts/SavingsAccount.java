package com.abc.accounts;

import java.util.Date;

/**
 * A class to represent one the account options available to the customer.
 * It inherits from the Account class.
 */
public class SavingsAccount extends Account {

    /**
     * Constructor for the class
     *
     * @param accountID A unique ID for the account.
     */
    public SavingsAccount(int accountID) {
        super(AccountType.Savings, accountID);
    }

    /**
     * A method to define the interest earned on this account type.
     *
     * @param balance      The amount the interest is earned on.
     * @param numberOfDays The number of days interest being earned.
     * @return the interest earned.
     */

    @Override
    public double interestEarned(Double balance, int numberOfDays, Date from) {

        /*
         * Compound daily interest Explained:
         * E.g if an account has a balance of 1500. You will earn interest of 0.001/365 on 1000 per day (interest001).
         * This interest will then be added to 500. Then you will earn interest of 0.002/365 on (500 + interest001) per day
         * Repeat for the number of days
         */

        // If account balance is less than or equal to 1000, interest of 0,001 annually.
        if (balance <= 1000) {
            return compoundFormula(balance, 0.001, numberOfDays);
        } else {
            // If anything more than interest of 0.001 annually on the first 1000 and 0.002 on the rest.
            double interest001 = compoundFormula(1000.00, 0.001, 1);
            double interest002;
            double interestBalance = balance;

            // Loop through for the number of days interest is being calculated for.
            for (int x = 0; x < numberOfDays; x++) {
                //Work out the interest on 1000 and add it to anything over 1000 and then calculate the interest on that.
                interest002 = compoundFormula(balance + interest001 - 1000.00, 0.002, 1);
                balance += interest002 + interest001;
            }

            return balance - interestBalance;
        }
    }
}
