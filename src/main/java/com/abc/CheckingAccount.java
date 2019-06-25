package com.abc;

public class CheckingAccount extends AccountTemp {

    private final double interestRate = 0.001;

    public CheckingAccount(){
        this.accountType = 0;
        this.accountTypeString = "Checking";
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            accountBalance -= amount;
        }
    }

    public double interestEarned() {
        return 0;
    }
}
