package com.abc;

import java.util.concurrent.TimeUnit;

public class MaxiSavingAccount extends Account {
    public MaxiSavingAccount(String name) {
        super(name);
    }

    public double interestEarned() {
        double amount = sumTransactions();

        if(getLastWithdrawal() != null) {
            long diff = DateProvider.getInstance().now().getTime() - getLastWithdrawal().getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if(days < 10 ) return amount * 0.01;
            else return amount * 0.05;
        }
        else return amount * 0.05;
    }

}
