package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*An abstract class to contain Account details : list of transactions 
 * 	See CheckingAccount.java for an example.


@author Hans-Wai Chan

*/

public abstract class Account {

    private List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    
    /**
     * Method to add a transaction with positive amount. For depositing money into bank account.
     * 
     * @param amount
     * 		double: amount of the transaction
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero. Not deposited");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Method to add a transaction with a negative amount. For withdrawing money from bank account.
     * @param amount
     */
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero. Not withdrawn");
	    } else {
	    	if (sumTransactions() >= amount) {
	    		transactions.add(new Transaction(-amount));
	    	}else {
	    		throw new IllegalArgumentException("Amount is greater than balance (total of account transactions). Not withdrawn");
	    		}
	    	}
	    
	}
	
	/**
	 * Method to get transactions of this account
	 * @return
	 * 		List <Transactions> : list of transactions of this account
	 */
    public List<Transaction> getTransaction() {
    	return transactions;
    }
	
	public abstract double interestEarned();
    
    
    /**
     * Method to calculate the effective daily interest from AER
     * @return
     * 		double: daily interest rate
     */
    public double interestEarnedDaily() {
    	return interestEarned()/365.0;
    }
    
    
    
    
    /**
     * Method to calculate the balance by adding up all the transactions on account
     * 
     * @return 
     * 		double: sum of all the transactions in account, balance. 
     */
    public double sumTransactions() {
		double amount = 0.0;
		if (checkIfTransactionsExist()) {
			for (Transaction t : transactions)
				amount += t.getAmount();
		}
		return amount;
    }

    private boolean checkIfTransactionsExist() {
		if (transactions.isEmpty()) {
				return false;
		}
		return true;
    }

    /**
     * Method to check to any withdrawals within the past 10 days
     * @return 
     * 		boolean: has the account made a withdrawal in the past 10 days?
     */
    public boolean HasWithdrawnPast10days() {
    		
    	
			DateProvider dateProv = new DateProvider();
			Date date10DaysAgo = dateProv.datePast(10); 
			int index = indexofLastWithdrawal();
	    	Date dateLastWithdrawal = transactions.get(index).getTransactionDate();
	    	
	    	if(dateLastWithdrawal.after(date10DaysAgo)) {
	    		return true;
	    	} else {
	    		return false;
	    	}
	}
    
    public int indexofLastWithdrawal() {
    	
    	List<Transaction> transactions = getTransaction(); 
		for (int index = transactions.size() - 1; index >= 0; index--) {
			if (transactions.get(index).getAmount() < 0) {
				return index;
			}
		}
		return 0; 
    }
}
