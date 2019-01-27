package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
	public enum AccountType {
			CHECKING,
			SAVINGS,
			MAXI_SAVINGS
	}
	
	public static final double CHECKING_RATE = 0.001;
	public static final double SAVINGS_RATE_FIRST_1000 = 0.001;
	public static final double SAVINGS_RATE_AFTER_1000 = 0.002;
	public static final double MAXI_RATE_FIRST_1000 = 0.02;
	public static final double MAXI_RATE_SECOND_1000 = 0.05;
	public static final double MAXI_RATE_AFTER_2000 = 0.1;
	
    private final AccountType accountType;
	private long customerID = -1;
    private List<Transaction> transactions;
	private double balance = 0;

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

    public double getInterestEarned() {
        double amount = balance;
        switch(accountType){
			case CHECKING:
				return amount * CHECKING_RATE;
            case SAVINGS:
                if (amount <= 1000)
                    return amount * SAVINGS_RATE_FIRST_1000;
                else
                    return 1 + (amount - 1000) * SAVINGS_RATE_AFTER_1000;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * MAXI_RATE_FIRST_1000;
                if (amount <= 2000)
                    return 20 + (amount - 1000) * MAXI_RATE_SECOND_1000;
                return 70 + (amount - 2000) * MAXI_RATE_AFTER_2000;
            default:
                return amount * CHECKING_RATE;
        }
    }

    public AccountType getAccountType() {
        return accountType;
    }
	
	public long getCustomerID(){
		return customerID;
	}

	public void setCustomerID(long newID){
		customerID = newID;
	}
	
	public double getBalance(){
		return balance;
	}
	
	public List<Transaction> getTransactions(){
		return transactions;
	}
}
