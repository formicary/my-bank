package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
    //TODO: these should be its own enum AccountType
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    //TODO: can be final
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    //TODO: no check here that the customer has sufficient funds to withdraw the amount
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        //TODO: instead of switching on accountType can have properties of an enum
        switch(accountType){
            case SAVINGS:
                //TODO: this doesnt match the specification in the readme, .1% on 1st 1k then .2% on anything above 1k
                if (amount <= 1000) {
                    return amount * 0.001;
                } else {
                    return 1 + (amount - 1000) * 0.002;
                }
                //TODO: there is no break here, yes the code above will return so it is not a bug, but a break will make the code more readable
                //TODO: SUPER_SAVINGS is not one of the options above nor is it listed in the readme, this code must be no longer needed and should be deleted not commented out
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.02;
                }
                if (amount <= 2000) {
                    return 20 + (amount - 1000) * 0.05;
                }
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    //TODO: unused param checkAll
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        //TODO: could be a stream
        for (Transaction t: transactions) {
            amount += t.amount;
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
