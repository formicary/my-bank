package com.abc.accounts;

import java.util.Date;

/**
 * A class to represent one the account options available to the customer.
 * It inherits from the Account class.
 */
public class CheckingAccount extends Account {

    /**
     * Constructor for the class
     * @param accountID A unique ID for the account.
     */
    public CheckingAccount(int accountID) {
        super(AccountType.Checking, accountID);
    }

    /**
     * A method to define the interest earned on this account type.
     * @param balance The amount the interest is earned on.
     * @param numberOfDays The number of days interest being earned.
     * @return the interest earned.
     */
    @Override
    public double interestEarned(Double balance, int numberOfDays, Date from) {
        // An interest rate of 0.001 annually.
        // This is converted into a daily rate by the compound formula and applied for the number of days
        return compoundFormula(balance, 0.001, numberOfDays);
    }
}
