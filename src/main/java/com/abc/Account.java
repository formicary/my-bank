/**
 * Account is a generalisation class which can be further specified in to Accounts that serve a specific purpose.
 */

package com.abc;

import java.util.ArrayList;
import java.util.List;


public class Account {

    private ArrayList<Transaction> transactions;
    private double balance;
    private double interest;


    /**
     * Create a basic account. An account type will need to be further specified to determine its key functionality's.
     * Create an empty list of transactions to be filled by a Customer.
     */

    public Account() {

        this.transactions = new ArrayList<Transaction>();
        interest = 0.001;
        balance = 0;
    }

    /**
     * Deposit funds in to this Account. Amount must be greater than 0!
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) throws InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException("Amount Must Be Greater Than Zero");
        }
        else {
            transactions.add(new Transaction(amount));
            setBalance(amount);
        }
    }

    /**
     * Withdraw funds from this Account. Amount must be greater than 0!
     * @param amount The amount to withdraw from this account.
     */
    public void withdraw(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount Must Be Greater Than Zero");
        }
        if (amount > balance) {
            throw new InvalidAmountException("Account Has Insufficient Funds To Withdraw: " + Formatter.toDollars(amount));
        }
        else {
            transactions.add(new Transaction(-amount));
            setBalance(-amount);
        }
    }

    /**
     * Set the balance of the account.
     * @param amount The amount to effect the balance by.
     */

    protected void setBalance (double amount) {
        this.balance += amount;
    }

    /**
     * Get the balance of the account.
     * @return The balance of the account.
     */

    protected double getBalance () {
        return balance;
    }

    /**
     * Get the annual interest on the account.
     * @return The annual interest on the account.
     */

    protected double getInterest () {
        return interest;
    }

    /**
     * Set the annual interest on the account.
     * @param interest The new interest.
     */

    protected void setInterest (double interest) {
        this.interest = interest;
    }




    public ArrayList<Transaction> getTransactions () {
        return transactions;
    }


    /**
     * Calculate the annual interest earned from a basic Account.
     * interest at 0.1%
     * @return The balance after annual interest is calculated. 
     */

    public double interestEarned() {

            return balance * interest;  // basic account
    }

    /**
     * Get the statement for an individual account with account name, all transactions and their amount and a total in account.
     * @return The statement for this account.
     */

    public String statementForAccount() {

        String s = "";

        s += this.toString();

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + Formatter.toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + Formatter.toDollars(total);
        return s;
    }

}
