package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    public double capital;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.capital = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, Transaction.DEPOSIT));
            this.capital += amount;
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else if (amount <= this.capital) {
	    	this.capital -= amount;
	        transactions.add(new Transaction(-amount, Transaction.WITHDRAW));
	    } else {
	    	throw new IllegalArgumentException("cannot withdraw more than account capital");
	    }
	}

	public void transfer(Account recieving, double amount) {
		this.withdraw(amount);
		recieving.deposit(amount);
	}
	
    public double interestEarned() {
    	Date lastWithdraw = this.lastWithdraw();
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * DateProvider.CalculateDailyCompound(0.001);
                else
                    return amount * DateProvider.CalculateDailyCompound(0.002);
            case MAXI_SAVINGS:
                if (DateProvider.olderThanTenDays(lastWithdraw))
                    return amount * DateProvider.CalculateDailyCompound(0.05);
                else 
                    return amount * DateProvider.CalculateDailyCompound(0.001);
            default:
                return amount * DateProvider.CalculateDailyCompound(0.001);
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
    
    public Date lastWithdraw() {
    	for (int i = this.transactions.size() - 1; i >= 0; i--) {
    		if (transactions.get(i).type == Transaction.WITHDRAW) {
    			return transactions.get(i).getTransactionDate();
    		}
    	}
    	return null;
    }

    public int getAccountType() {
        return accountType;
    }

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}
    
}
