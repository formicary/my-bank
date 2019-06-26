package com.abc;

public class CheckingAccount extends Account {

    private final double interestRate = 0.001;

    public CheckingAccount(){
        this.accountType = 0;
        this.accountTypeString = "Checking";
    }

    public void withdraw(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("error: amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(-amount, Transaction.WITHDRAWAL));
            this.deductFunds(amount);
        }

    }

    public double interestEarned() {

        // balance of
        if(this.accountBalance <= 0) return 0.0;

        return this.accountBalance * this.interestRate;
    }
}
