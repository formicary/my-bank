package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (sumTransactions() - amount < 0) {
            throw new IllegalArgumentException("balance too low to withdraw");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case CHECKING:
                return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 10 + ((amount-1000) * 0.002); // 0.1% of 1000 = 10
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + ((amount-1000) * 0.05); // 2% of 1000 = 20
                return 20 + 50 + ((amount-2000) * 0.1); // 2% of first 1000 = 20, // 5% of next 1000 = 50, total = 70
            default:
                throw new IllegalArgumentException("invalid account type. valid account types are: 'SAVINGS', 'MAXI_SAVINGS', and 'CHECKING' accounts");
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
