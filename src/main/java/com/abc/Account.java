package com.abc;

import java.util.ArrayList;
import java.util.List;

//Lewis Nangle
//lewisnangle1@gmail.com

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public double balance = 0;
    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(this,amount,Transaction.DEPOSIT));
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(this,-amount,Transaction.WITHDRAWL));
    }
}

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
            		if (!transactions.isEmpty()) {
            			Transaction mostRecent = transactions.get(transactions.size()-1);
            			if (mostRecent.checkTransactionIsInLastTenDays()) {
            				//most recent transaction was within the last 10 days
            				return amount * 0.05;
            			} else {
            				//most recent transaction was not within last 10 days
            				return amount * 0.01;
            			}

            		} //Throw Error here or not?
            		/*
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
                */
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

    public int getAccountType() {
        return accountType;
    }


    public void transferBetweenAccounts(Account incoming, double amount) {
    	if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        		Account outgoing = this;
            transactions.add(new Transaction(outgoing,-amount,Transaction.TRANSFER));	//add new negative transaction to outgoing account
            incoming.transactions.add(new Transaction(incoming,amount,Transaction.TRANSFER));	//add new positive transaction to incoming account
        }
    }

}
