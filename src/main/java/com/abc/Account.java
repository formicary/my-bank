package com.abc;

import java.util.ArrayList;
import java.util.List;


public class Account {
    public static double savingsInterestRateLow = 0.001;
    public static double savingsInterestRateHigh = 0.002;
    public static double maxiSavingsInterestRateFewDays = 0.001;
    public static double maxiSavingsInterestRateManyDays = 0.05;
    public static double checkingInterestRate = 0.001;
    public static double numberOfDays = 365.0;

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public double moneyIn = 0.0;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount, boolean payment) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            moneyIn += amount;
            transactions.add(new Transaction(amount, payment));
        }
    }

public void withdraw(double amount, boolean payment) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        if (amount <= moneyIn) {
            moneyIn -= amount;
            transactions.add(new Transaction(-amount, payment));
        } else {
            throw new IllegalArgumentException("you don't have enough money for this transaction");
        }

    }
}

    public double interestEarned(boolean dailyAccrue) {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    amount *= savingsInterestRateLow;
                else
                    amount = 1 + (amount-1000) * savingsInterestRateHigh;
                break;
            case MAXI_SAVINGS:
                if (DateProvider.numberOfDaysPassed(10, transactions))
                    amount *= maxiSavingsInterestRateManyDays;
                else
                    amount *= maxiSavingsInterestRateFewDays;
                break;
            case CHECKING:
                amount *= checkingInterestRate;
                break;
        }
        if(dailyAccrue == true)
            return amount/numberOfDays;
        else
            return amount;
    }

    public double sumTransactions(){
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
