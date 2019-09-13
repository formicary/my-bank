package com.abc.Account;

public class CheckingAccount extends Account {

    public double interestEarned() {
        double amount = sumTransactions();
        return amount*0.001;
    }

}
