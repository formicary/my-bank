package com.abc;

import java.time.LocalDate; // changed all methods to implement new up to data Java Date class
import java.util.ArrayList;
import java.util.List;


public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    public static final int SUPER_SAVINGS = 3;

    private final int accountType;
    private double balance; // added to include the current balance ona  given account
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = getBalance(); // added to include balance of an account. Starts at 0 prior to deposit
    }

    // getter for the account balance
    public double getBalance() {
        return sumTransactions();
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
        } else if (amount > getBalance()) { // added to prevent withdrawing non-existent funds from the account
        throw new IllegalArgumentException("insufficient funds for this transaction");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    // New method to transfer funds between accounts of a given customer
    public void transfer(Customer c, double amount, Account a, Account b) {
        if (c.accountPresent(a) && c.accountPresent(b)) {
            try {
                a.withdraw(amount);
                b.deposit(amount);
                System.out.println("Transaction completed.");
            } catch (IllegalArgumentException e) {
                System.err.println("Transfer failed.\nPlease check transfer amount and account balance");
            }
        } else {
            throw new IllegalArgumentException("One or more of the requested " +
                    "accounts do not exist for this customer.");
        }
    }

    // method changed to include Super Savings account, include new interest rates for Maxi Savings accounts
    public double interestEarned() {
        double amount = getBalance(); // change amount to the account's balance
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case SUPER_SAVINGS: // Included Super Savings account
                if (amount <= 4000)
                  return amount + 20; // changed to return amount + 20 (presumed £20 bonus in Super Savings, could not be interest rate)
            // changed to have a rate 5% assuming no withdrawals in the last 10 days, otherwise 0.1%
            case MAXI_SAVINGS:
                for (int i = 0; i < transactions.size(); i++) {
                    if (transactions.get(i).getTransactionDate().isBefore(LocalDate.now().minusDays(10))) {
                        return amount * 0.0001;
                    } else {
                        continue;
                    }
                }
                return amount * 0.05;
            default:
                return amount * 0.001; // checking accounts 0.1%
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
