package com.abc.account_types;

import com.abc.Constants.AccountTypes;
import com.abc.Constants;
import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class BaseAccount {
    public List<Transaction> transactions;

    public BaseAccount(){
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(Constants.GreaterThanZeroErrorMessage);
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(Constants.GreaterThanZeroErrorMessage);
        } else {
            // Don't like the - here, could be missing
            transactions.add(new Transaction(-amount));
        }
    }

    // Protected maybe? Don't like how there's to of the same loop below
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public String getAccountSummary(){
        AccountTypes accountType = getAccountType();

        String summary = accountType.toString() + "\n";
        double total = 0.0;
        for (Transaction t : transactions) {
            summary += (t.amount < 0 ? "Withdraw: " : "Deposit: ") + toDollars(t.amount) + "\n";
            total += t.amount;
        }

        summary += "Total: " + toDollars(total);

        return summary;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", d);
    }

    abstract public AccountTypes getAccountType();
    abstract public double getInterestEarned();
}
