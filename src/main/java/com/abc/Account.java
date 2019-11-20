package com.abc;

import com.abc.utils.Formatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Class correlating to bank accounts.
 */
class Account {
    private final AccountType accountType;

    private double balance = 0;
    private List<Transaction> transactions;

    Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Deposits funds into this account.
     * @param amount the amount of money to deposit into the account
     */
    void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("The deposit amount must exceed zero.");
        else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    /**
     * Withdraws funds from this account.
     * @param amount the amount of money to withdraw from the account
     */
    void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("The withdrawal amount must exceed zero.");
        else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    /**
     * Operating under the assumption that the statements:
     * "Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%"
     * "Maxi-Savings accounts have a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%"
     * Indicate the rate that applies for a balance
     *
     * Determines the interest earned by the bank account.
     * @return the amount made via interest for this account
     */
    double calcInterest() {
        switch (accountType) {
            case SAVINGS:
                return (balance <= 1000) ? balance * 0.001 : 1 + (balance - 1000) * 0.002;
            case MAXI_SAVINGS:
                if (balance <= 1000) return balance * 0.02;
                else if (balance <= 2000) return 20 + (balance - 1000) * 0.05;
                else return  70 + (balance - 2000) * 0.1;
            default:
                return balance * 0.001;
        }
    }

    /**
     * Generates an account statement, composing of a balance and record for each transaction.
     * @return the account statement
     */
    String genStatement() {
        StringBuilder statement = new StringBuilder(getAccountTypeToString());
        statement.append(" - Total: ");
        statement.append(balanceToString());
        statement.append('\n');

        // Appending information about each transaction.
        for (Transaction transaction : transactions) {
            statement.append("\t");
            statement.append((transaction.amount < 0 ? "Withdrawal: " : "Deposit: "));
            statement.append(Formatting.toDollars(transaction.amount));
            statement.append('\n');
        }

        return statement.toString();
    }

    double getBalance() {
        return balance;
    }

    /**
     * Returns the balance of the account as a monetary string.
     * @return the balance of the account in dollars
     */
    String balanceToString() {
        return Formatting.toDollars(balance);
    }

    List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Checks for the existence of transactions within this account.
     * @return whether the account has made any transactions
     */
    boolean hasTransactions() {
        return !transactions.isEmpty();
    }

    AccountType getAccountType() {
        return accountType;
    }

    private String getAccountTypeToString() {
        switch (accountType) {
            case SAVINGS:
                return "Savings Account";
            case MAXI_SAVINGS:
                return "Maxi Savings Account";
            default:
                return "Checking Account";
        }
    }
}
