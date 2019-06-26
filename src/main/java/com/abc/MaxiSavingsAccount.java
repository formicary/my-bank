package com.abc;

public class MaxiSavingsAccount extends Account {

    protected final double initialInterestRate;
    protected final double higherInterestRate;
    protected final double topInterestRate;

    public MaxiSavingsAccount(){
        this.accountType = 2;
        this.accountTypeString = "Maxi-Savings";

        this.initialInterestRate = 0.02;
        this.higherInterestRate = 0.05;
        this.topInterestRate = 0.1;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if(!this.hasSufficientFunds(amount)){
            throw new IllegalArgumentException("error: insufficient funds for withdrawal");
        } else {
            transactions.add(new Transaction(-amount, Transaction.WITHDRAWAL));
            this.deductFunds(amount);
        }
    }

    public double interestEarned() {
        if(this.accountBalance <= 1000.0) {
            return this.accountBalance * this.initialInterestRate;
        } else if(this.accountBalance <= 2000.0){
            return 20 + (this.accountBalance-1000.0) * this.higherInterestRate;
        }else{
            return 70 + (this.accountBalance-2000.0) * this.topInterestRate;
        }
    }
}