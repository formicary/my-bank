package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * TASK
 * Different accounts have interest calculated in different ways:
 * -Checking accounts have a flat rate of 0.1%
 * -Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
 * -Maxi-Savings accounts have a rate of 2% for the first $1,000 then 5% for the next $1,000
 * then 10%
 * <p>
 * Additional
 * -Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the
 * past 10 days otherwise 0.1%
 * -Interest rates should accrue and compound daily (incl. weekends), rates above are per-annum
 */

/**
 * This class represents all Accounts a Customer can open.
 */
public class Account {
    // TODO: 11/10/2019 Make this class an interface
    // TODO: 11/10/2019 Use subclasses to represent different accounts
    //Account ids
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    //Current account type
    private final int accountType;  //defaults to CHECKING

    //List of all transactions of this account
    public List<Transaction> transactions;

    /**
     * Initializes a new Account where the type matches the argument given.
     *
     * @param accountType the account id
     */
    public Account(int accountType) {
        // TODO: 10/10/2019 correct account validation
        if (accountType < CHECKING || accountType > MAXI_SAVINGS) {
            throw new IllegalArgumentException("please enter correct account type");
        }
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Add money to this Account. The transaction is added to list of transaction.
     *
     * @param amount amount in decimal
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Withdraw from this Account. The transaction is added to list of transaction.
     *
     * @param amount amount in decimal
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * Calculate the interest earned of the current account.
     *
     * @return interest earned
     */
    public double interestEarned() {
        // TODO: 10/10/2019 Remove magic numbers
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount - 1000) * 0.05;
                return 70 + (amount - 2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    /**
     * @return
     */
    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    /**
     * @param checkAll
     * @return
     */
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }

    /**
     * Returns the type of the current account.
     *
     * @return the account type
     */
    public int getAccountType() {
        return accountType;
    }

}
