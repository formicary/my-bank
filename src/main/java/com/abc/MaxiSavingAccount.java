package com.abc;

public class MaxiSavingAccount extends Account {

    private final double interestRate = 0.001;

    public MaxiSavingAccount(){
        this.accountType = 0;
        this.accountTypeString = "Checking";
    }

    public void withdraw(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("error: amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(-amount));
            this.accountBalance -= amount;
        }


    }

    public double interestEarned() {

        if(this.accountBalance <= 0){
            return 0.0;
        }

        return this.accountBalance * this.interestRate;
    }
}
