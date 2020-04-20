package com.abc.account;

import java.util.Date;

public class SavingsAccountStrategy implements AccountStrategy {

    /**
     * interestEarned
     * rate of 0.1% for the first $1,000 then 0.2%
     *
     * @param amount
     * @param dateOfLastWithdrawal
     * @return
     */
    @Override
    public double interestEarned(double amount, Date dateOfLastWithdrawal) {
        if (amount <= 1000) {
            return amount * 0.001;
        } else {
            return 1 + (amount - 1000) * 0.002;
        }
    }
}
