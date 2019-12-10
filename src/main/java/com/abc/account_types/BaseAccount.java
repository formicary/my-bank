package com.abc.account_types;

import com.abc.Constants.AccountTypes;
import com.abc.Constants;
import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAccount {
    public List<Transaction> transactions;

    public BaseAccount(){
        this.transactions = new ArrayList<Transaction>();
    }

    abstract public AccountTypes getAccountType();
    abstract public double getInterestEarned();

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
            transactions.add(new Transaction(-amount));
        }
    }

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
            summary += "- " + (t.amount < 0 ? "Withdraw: " : "Deposit: ") + toDollars(t.amount) + "\n";
            total += t.amount;
        }

        summary += "Total: " + toDollars(total);

        return summary;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", d);
    }
}
