package com.abc.account;

import com.abc.util.DateProvider;

import java.util.Date;

public class MaxiSavingsAccountStrategy implements AccountStrategy {

    /**
     * interestEarned
     * rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%
     * and extra interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
     *
     * @param amount
     * @param dateOfLastWithdrawal
     * @return
     */
    @Override
    public double interestEarned(double amount, Date dateOfLastWithdrawal) {
        double result;

        if (amount <= 1000) {
            result = amount * 0.02;
        } else if (amount <= 2000) {
            result =  20 + (amount - 1000) * 0.05;
        } else {
            result =  70 + (amount - 2000) * 0.1;
        }

        Date currentDateBefore10Days = DateProvider.getInstance().getCurrentDateBefore10Days();
        if (dateOfLastWithdrawal != null && dateOfLastWithdrawal.before(currentDateBefore10Days)) {
            result = result * 0.005;
        }

        return result;
    }
}
