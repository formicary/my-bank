package com.abc.customer;

import com.abc.helper.DateProvider;
import com.abc.helper.Interest;
import com.abc.helper.Transactions;
import com.abc.transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * For a given account type, stores a list of associated transactions
 */
public class Account {

    private final AccountType accountType;  // the account type enum (used for calculating interest earned)
    private List<Transaction> transactions;  // the collection of transactions

    /**
     * Constructor, which simply requires an account type to be defined
     * @param accountType the account type enum instance
     * TODO: may require a Customer relationship to be added
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
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
        List<Transaction> chronologicalTransactions = Transactions.sortByDate(transactions);
        return Interest.calculateYield(chronologicalTransactions, accountType);
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

    List<Transaction> getTransactions() {
        return transactions;
    }


    List<Transaction> getWithdrawals() {
        List<Transaction> withdrawals = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (t.amount < 0)  withdrawals.add(t);
        }
        return withdrawals;
    }


}
