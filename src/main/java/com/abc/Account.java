package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final double SAVINGS_INITIAL_INTEREST_RATE = 0.001;
    public static final double SAVINGS_FINAL_INTEREST_RATE = 0.002;
    public static final double MAXI_SAVINGS_INITIAL_INTEREST_RATE = 0.001;
    public static final double MAXI_SAVINGS_TEN_DAYS_SPECIAL_INTEREST_RATE = 0.05;
    public static final double CHECKING_INTEREST_RATE = 0.001;

    public static final double daysPerAnnum = 365.0;

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private double money;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.money = 0.0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            money += amount;
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if(amount <= money){
                money -= amount;
                transactions.add(new Transaction(-amount));
            }else{
                throw new IllegalArgumentException("Insufficient funds");
            }
        }
    }

    public double interestEarned() {
        double amount = money;

        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return (amount * SAVINGS_INITIAL_INTEREST_RATE)/daysPerAnnum;
                else
                    return (1 + (amount-1000) * SAVINGS_FINAL_INTEREST_RATE)/daysPerAnnum;

            case MAXI_SAVINGS:
                if (DateProvider.tenDaysPassed(10, transactions))
                    return (amount * MAXI_SAVINGS_TEN_DAYS_SPECIAL_INTEREST_RATE)/daysPerAnnum;
                else
                    return (amount * MAXI_SAVINGS_INITIAL_INTEREST_RATE)/daysPerAnnum;
            default:
                return (amount * CHECKING_INTEREST_RATE)/daysPerAnnum;
        }
    }

    public int getAccountType() {
        return accountType;
    }

    public double getMoney(){
        return money;
    }


}
