package com.abc.Account;

import java.util.Date;

public class SavingsAccount extends Account {

    public SavingsAccount() {
        super(AccountType.SAVINGS);
    }

    @Override
    protected double applyInterest(double amount, Date lastWithdrawal) {
        if (amount <= 1000)
            return amount * (0.001/365);
        else
            return 0.0027397260273972603 + (amount-1000) * (0.002/365);
    }

//    @Override
//    public double interestEarned() {
//        double amount = super.sumTransactions();
//        if (amount <= 1000)
//            return amount * 0.001;
//        else
//            return 1 + (amount-1000) * 0.002;
//    }
}
