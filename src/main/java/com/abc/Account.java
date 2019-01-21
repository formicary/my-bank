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
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount deposited must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount withdrawn must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
			case AccountType.CHECKING:
				return amount * 0.001;
            case AccountType.SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case AccountType.MAXI_SAVINGS:
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
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
