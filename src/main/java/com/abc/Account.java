package com.abc;

import java.util.ArrayList;
import java.util.List;

/* -- Account Class --
    An object that represents a Account. Is Abstract: An Account should never 
    be instantiated, instead should be either a Checking, Savings or Maxi. 
        An Account has the following methods and public variables:
            -public List<Transaction> transactions;

            -Account(account_type accountType) - Returns an account of the 
            speciied type. accountTypes can be SAVINGS, MAXI_SAVINGs or 
            CHECKING.
            -void deposit(double amount) - Deposits the specified amount of
            money into the account. amount should be greater than 0. 
            -void withdraw(double amount) - Withdraws the specified amount of
            money from the account. amount should be greather than 0. 
            -double interestEarned() - Calculates the amount of money the 
            account is going to earn. 
            -double sumTransactions() - Returns the amount of money in the 
            account. 
            -account_type getAccountType() - Returns the account type of the
            account. 
            -transfer_status transferMoneyToAccount(double amount, 
            Account target) - transfers amount from called Account to target 
            account. It's assumed that accounts are allowed to transfer
            money even if it results in the account going into negative
            values. Hence, transfer_status is always a success. 
            
*/
public abstract class Account {

    private final account_type accountType;
    public List<Transaction> transactions;

    // Returns an account of the 
    // speciied type. accountTypes can be SAVINGS, MAXI_SAVINGS or 
    // CHECKING.
    public Account(account_type accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    // Deposits the specified amount of money into the account. Logged as a 
    // transaction. 
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(amount));
        }
    }

    // Withdraws the specified amount of money from an account. Logged as a
    // transaction. 
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(-amount));
        }
    }

    // Calculates the amount of interest the account is going to earn. 
    public abstract double interestEarned();

    // Returns the total amount of money in the account. 
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    // Returns the account type of the account. 
    public account_type getAccountType() {
        return this.accountType;
    }

    // Attempts to transfer money from called account to target. 
    public transfer_status transferMoneyToAccount(double amount, 
                                                  Account target) {
        // Following paradigm set in rest of the class, attempting to transfer
        // amount less than 0 throws an error. 
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.withdraw(amount);
            target.deposit(amount);
            return transfer_status.Success;
        }
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: this.transactions)
            amount += t.amount;
        return amount;
    }

}
