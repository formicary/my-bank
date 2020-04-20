package com.abc.account;

import java.util.Date;

public class CheckingAccountStrategy implements AccountStrategy {

    /**
     * interestEarned
     * rate of 0.1%
     *
     * @param amount
     * @param dateOfLastWithdrawal
     * @return
     */
    @Override
    public double interestEarned(double amount, Date dateOfLastWithdrawal) {
        return amount * 0.001;
    }
}
