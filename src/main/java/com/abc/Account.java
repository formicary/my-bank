package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
	private double balance;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
		this.balance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
			balance += amount;
        }
    }

public boolean withdraw(double amount) {
	boolean complete = false;
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
		if (amount > balance){
			throw new IllegalStateException("amount must be below account's balance")
		}
		else{
			transactions.add(new Transaction(-amount));
			complete = true;
			balance -= amount;
		}
		return complete;
    }
}

    public double interestEarned() {
		boolean withdrawal = checkWithdrawal();
        switch(accountType){
            case SAVINGS:
                if (balance <= 1000)
                    return (balance * 0.001) / 365;
                else
                    return (1 + (balance-1000) * 0.002)/365;
            case MAXI_SAVINGS:
                if (withdrawal == true)
                    return (balance * 0.001) / 365;
                else
                    return (balance * 0.05) / 365;
            default:
                return (balance * 0.001) / 365;
        }
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountType() {
        return accountType;
    }

	public boolean checkWithdrawal(){
		boolean withdrawal = false;
		Calendar interestInterval = Calendar.getInstance();
		interestInterval.add(Calendar.DAY_OF_MONTH, -10).
		for (int t = transactions.size(); t >= 0; t--) {
			boolean done = false;
			if (t.getDate < interestInterval){
				break;
			} else{
    			if (t.amount < 0){
        			withdrawal = true;
					break;
				}
			}
		}
		return withdrawal;
	}

}
