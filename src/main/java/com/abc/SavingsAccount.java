package com.abc;

public class SavingsAccount extends AccountTemp {

    protected final double initialInterestRate;
    protected final double higherInterestRate;

    public SavingsAccount(){
        this.accountType = 1;
        this.accountTypeString = "Savings";
        this.initialInterestRate = 0.001;
        this.higherInterestRate = 0.002;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if(this.accountBalance - amount < 0.0){
            throw new IllegalArgumentException("error: insufficient funds for withdrawal");
        } else {
            transactions.add(new Transaction(-amount));
            this.accountBalance -= amount;
        }
    }

    public double interestEarned() {
        return 0;
    }
}
