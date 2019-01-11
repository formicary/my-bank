package com.abc;

public class SavingsAccount extends Account{


    public double interestEarned() {
        double amount = sumTransactions();

        if (amount <= 1000){
            return amount * 0.001;
        }

        return 1 + (amount - 1000) * 0.002;

    }

    public String statementForAccount() {
        String s = "Savings Account\n";
        return s + totalTransactions();
    }
}
