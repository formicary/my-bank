package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
	public enum AccountType {
			CHECKING,
			SAVINGS,
			MAXI_SAVINGS
	}

    private final AccountType accountType;
	private long customerID = -1;
    private List<Transaction> transactions;
	private double balance = 0;
	private boolean hasTransactionsInLast10Days; // for MAXI_SAVINGS accounts

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount deposited must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
			balance += amount;
        }
    }

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount withdrawn must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
			balance -= amount;
		}
	}
	
	public void transfer(double amount, Account otherAccount) {	
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount transferred must be greater than zero");
		} else {
			if(this.customerID == otherAccount.getCustomerID()){
				withdraw(amount);
				otherAccount.deposit(amount);
			} else {
				throw new IllegalArgumentException("Accounts are owned by two different customers");
			}
		}
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
			case CHECKING:
				return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
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

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }
	
	public long getCustomerID(){
		return customerID;
	}

	public void setCustomerID(long newID){
		this.customerID = newID;
	}
	
	public double getBalance(){
		return balance;
	}
	
	public List<Transaction> getTransactions(){
		return transactions;
	}
}
