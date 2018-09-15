package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Account} records any deposits and withdrawals, and interest earned.
 */
public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    
    /**
     * Constructs a new {@link Account}.
     * @param accountType The account type.
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    
    /**
     * Deposits money into the {@link Account}.
     * @param amount The amount of money to deposit.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }
    
    /**
     * Withdraws money from the {@link Account}.
     * @param amount The amount of money to withdraw.
     */
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}
	
	/**
	 * Calculates the interest earned by an {@link Account}.
	 * @return The interest earned.
	 */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }
    
    /**
     * Sums the transactions of the {@link Account}.
     * @return the sum of the transactions.
     */
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }
    
    /**
     * Returns the {@link Account} type.
     * @return See above.
     */
    public int getAccountType() {
        return accountType;
    }

}
