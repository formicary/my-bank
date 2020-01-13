package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
	
	private double balance;

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	balance += amount;
            transactions.add(new Transaction(amount, balance));
            
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
    	balance -= amount;
        transactions.add(new Transaction(-amount, balance));
        
    }
}

    public double interestEarned() {
        //double amount = sumTransactions();
    	double amount = balance;
        switch(accountType){
        	case CHECKING:
        		return (amount * 0.001);
            case SAVINGS:
                if (amount <= 1000)
                    return (amount * 0.001);
                else
                    return (1 + ((amount-1000) * 0.002));

            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
//              case SUPER_SAVINGS:
//              if (amount <= 4000)
//                  return 20;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       double sum = 0.0;
       
       if (checkIfTransactionsExist(true))	{
    	   for (Transaction t: transactions) {
    		   sum += t.amount;
    	   }
       }
       
       return sum;
    }

    private boolean checkIfTransactionsExist(boolean checkAll) {
    	
    	return (transactions.size() > 0);
    	
    }

    
    public int getAccountType() {
        return accountType;
    }
    
    public double getBalance()	{
    	return balance;
    }

}
