package com.abc;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * A class to represent an account
 */
@Data
public class Account {

    public static final String MSG_ERROR_IF_AMOUNT_IS_NEGATIVE = "Amount must be greater than zero!";

    private final AccountType accountType;
    private List<Transaction> transactions = new ArrayList<Transaction>();

    /**
     * Creates a new account.
     * @param accountType the type of the account
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
    }
    
    /**
     * Deposit an amount on the account.
     * @param amount to be deposited
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(MSG_ERROR_IF_AMOUNT_IS_NEGATIVE);
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Wihtdraw an amount from the account.
     * @param amount to be withdrawn
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(MSG_ERROR_IF_AMOUNT_IS_NEGATIVE);
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * Counts the interest on the account.
     * @return the interest earned
     */    
    public double interestEarned() {
        double amount = sumTransactions();
        double interest = 0;
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000) {
                    interest = amount * 0.001;
                }
                else {
                    interest = 1 + (amount-1000) * 0.002;
                }
                break;
            case MAXI_SAVINGS:
                if (amount <= 1000) {
                    interest = amount * 0.02;
                }
                if (amount <= 2000) {
                    interest =  20 + (amount-1000) * 0.05;
                }
                else {
                    interest =  70 + (amount-2000) * 0.1;
                }
                break;
            default:
                interest =  amount * 0.001;
        }
        return interest;
    }

    /**
     * Sums up the transactions on the account.
     * @return the sum of the accounts
     */
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;    
    }

}
