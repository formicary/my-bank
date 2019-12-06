package com.abc.account_types;

import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Account {

// Enum? 
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    // Is this supposed to be public?
    public List<Transaction> transactions;

    public Account(int accountType) {
        // Potential for Factory?
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        // It's allowing when depositing 0?
        if (amount <= 0) {
            // Should be switching the error messages to resources/constants
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

// Need to check the number types
public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        // Don't like the - here, could be missing
        transactions.add(new Transaction(-amount));
    }
}

    public double interestEarned() {
        double amount = sumTransactions();
        // Can bring this logic down into seperate classes, can make account abstract
        // What happens when less than 0?
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                // This logic is very confusing
                    return 1 + (amount-1000) * 0.002;
                    // Don't think we need super savings
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    // Are these decimals wrong?
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        // What if transactions is empty?
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    // If using enum, we need to make this method work
    public int getAccountType() {
        return accountType;
    }

}
