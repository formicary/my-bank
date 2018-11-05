package com.abc;

public class CheckingAccount extends Account {

    public CheckingAccount () {
        super();
    }

    /**
     * Calculate the interest earned from a CheckingAccount.
     * interest at 0.1%
     * @return The interest earned.
     */

    @Override
    public double interestEarned () {
        return getBalance() * 0.001;
    }

    /**
     * String representation to be displayed on statements.
     */
    @Override
    public String toString() {
        return "Checking Account\n";
    }
}
