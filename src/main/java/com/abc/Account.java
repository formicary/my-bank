package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
	        case CHECKING:
	            return checkingInterest(amount);
            case SAVINGS:
            	return savingsInterest(amount);
            case MAXI_SAVINGS:
            	return maxiSavingsInterest(amount);
            default:
            	throw new IllegalArgumentException("Account type not recognised");
        }
    }
    
    private double savingsInterest(double amount) {
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
    }
    
    private double maxiSavingsInterest(double amount) {
                if (amount <= 1000) {
                    return amount * 0.02;
                }else if (amount <= 2000) {
                    return 20 + (amount-1000) * 0.05;
                }else {
                	return 70 + (amount-2000) * 0.1;
                }
    }
    
    private double checkingInterest(double amount) {
                return amount * 0.001;
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
