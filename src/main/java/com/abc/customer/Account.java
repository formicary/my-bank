package com.abc.customer;

import com.abc.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * For a given account type, stores a list of associated transactions
 */
public class Account {

    private final AccountType accountType;  // the account type enum (used for calculating interest earned)
    List<Transaction> transactions;  // the collection of transactions

    /**
     * Constructor, which simply requires an account type to be defined
     * @param accountType the account type enum instance
     * TODO: may require a Customer relationship to be added
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Adds a transaction for a given amount to the List
     * @param amount the value of the transaction (negative = withdrawal, positive = transaction)
     */
    public void addTransaction(double amount){
        transactions.add(new Transaction(amount));
    }

    /**
     * Returns the interest that has been earned across all of the transactions
     * TODO: may require some checks for negative amounts (as these may not mean interest has been earned)
     * @return the double amount of interest that has been earned
     */
    double interestEarned() {
        double amount = sumTransactions();

        switch(accountType){

            case SAVINGS:
                if (amount <= 1000)     return amount * 0.001;
                else                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;

            case MAXI_SAVINGS:
                if (amount <= 1000)     return amount * 0.02;
                if (amount <= 2000)     return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;

            default:                    return amount * 0.001;
        }
    }

    /**
     * Sums over all of the transactions
     * @return the double total of all the transaction amounts
     */
    double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    AccountType getAccountType() {
        return accountType;
    }

}
