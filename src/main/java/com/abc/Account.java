package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
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
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case CHECKING:
            	return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                }
                else {
                    return 1 + (amount-1000) * 0.002;
                }
            case MAXI_SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.02;
                }
                else if (amount <= 2000) {
                    return 20 + (amount-1000) * 0.05;
                }
                else {
                    return 70 + (amount-2000) * 0.1;
                }
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       double amount = 0.0;
       for (Transaction t: transactions) {
           amount += t.getAmount();
       }
       return amount;
    }

    private boolean checkIfTransactionsExist() {
        int numberOfTransactions = transactions.size();
        if (numberOfTransactions > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getAccountType() {
        return accountType;
    }
    
    public List<Transaction> getTransactions(){
    	return transactions;
    }

}
