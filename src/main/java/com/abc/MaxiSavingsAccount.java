package com.abc;

public class MaxiSavingsAccount extends AccountTemp {

    public MaxiSavingsAccount(){
        this.accountType = 2;
        this.accountTypeString = "Maxi-Savings";
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
