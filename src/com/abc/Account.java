package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public static final double CHECKING_INTEREST = 0.001;
    public static final double SAVINGS_INTEREST_1 = 0.001;
    public static final double SAVINGS_INTEREST_2 = 0.002;
    public static final double MAXI_INTEREST_1 = 0.001;
    public static final double MAXI_INTEREST_2 = 0.05;
    public static final int MAXI_INTEREST_2_THRESH_DAYS = 10;

    private final int accountType;
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

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else { //TODO if no overdraft must check if enough money in the account
            transactions.add(new Transaction(-amount));
        }
    }

    public void transfer(double amount, Account recipient) {
        /**
         * Transfers the amount from THIS account to recipient's account
         */
        // no need to check amount since withdraw and deposit both do it
         this.withdraw(amount);
         recipient.deposit(amount);
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * SAVINGS_INTEREST_1;
                else
                    return 1 + (amount-1000) * SAVINGS_INTEREST_2;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                for (Transaction t: transactions) {
                    if(t.before(MAXI_INTEREST_2_THRESH_DAYS))
                        return amount * MAXI_INTEREST_1;
                }
                return amount * MAXI_INTEREST_2;
            default:
                return amount * CHECKING_INTEREST;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
