package com.abc;

import java.util.Date;

public class MaxiSavingsAccount extends Account {

    @Override
    protected double computeInterest(double amount) {
        if (noWithdrawalInThePast(10)) {
            return amount * 0.05;
        } else {
            return amount * 0.001;
        }
    }

    private boolean noWithdrawalInThePast(int numberOfDays) {
        Date today = DateProvider.getInstance().now();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                continue;
            }
            Date dateOfTransaction = t.getDate();
            if (Utils.dateDifferenceinDays(today, dateOfTransaction) <= numberOfDays) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Maxi Savings Account";
    }
    
}
