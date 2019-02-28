package com.abc;

public class SavingsAccount extends Account{

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        double interest = 0;
        if (amount >= 1000) {
            interest += 1000 * (0.1/365);
            interest += (amount - 1000) * (0.2/365);
        }
        this.deposit(interest);
        return interest;
    }

    @Override
    public String getAccountType() {
        return "Savings Account\n";
    }
}
