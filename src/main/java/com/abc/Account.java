package com.abc;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private double  balance;
    public List<Transaction> transactions;

    public Account(int accountType) {
    	this.balance = 0;
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	balance += amount;	// update account balance
            transactions.add(new Transaction(amount, balance));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else if (amount > balance) {
	        throw new IllegalArgumentException("insufficient funds to make transaction");	
	    } else {
	    	balance -= amount;	// update account balance
	        transactions.add(new Transaction(-amount, balance));
	    }
	}
	
	public void transfer(Account a, double amount) {
		if (this == a) {
			throw new IllegalArgumentException("cannot transfer money to the same account");
		} else {
			// exception checks for withdrawal and transfer are within the corresponding functions
			withdraw(amount);
			a.deposit(amount);
		}
	}

	public double getBalance() {
		return balance;
	}
	
    public double interestEarned() {
        double amount = balance;
        switch(accountType) {
        	case CHECKING:
        		return (amount * 0.001);	// rate constantly 0.1%
            case SAVINGS:
                if (amount <= 1000) {
                    return (amount * 0.001);	// first $1,000 has rate of 0.1%
                } else {
                    return (1 + ((amount - 1000) * 0.002)); // rate beyond $1,000 is 0.2% (0.1% of the first $1,000 = $1, then 0.2% of the remainder)
                }
            case MAXI_SAVINGS:
            	int latestWithdrawalIndex = getLatestTransactionIndex("withdrawal");
            	if (latestWithdrawalIndex == -1) {
            		return (amount * 0.05);	// if no withdrawals made at all, rate = 5% 
            	} else {
	            	if (daysSinceTransaction(transactions.get(latestWithdrawalIndex), new Date()) > 10) {
	            		return (amount * 0.05);		// if last withdrawal over 10 days ago, rate = 5%
	            	} else {
	            		return (amount * 0.001);	// if last withdrawal within last 10 days, rate = 0.1%
	            	}
            	}
            	/* Old Maxi-Savings interest rate
                if (amount <= 1000) {
                    return (amount * 0.02);	// first $1,000 has rate of 2%
                } else if (amount <= 2000) {
                    return (20 + ((amount - 1000) * 0.05));	// rate for second $1,000 is 5% (2% of the first $1,000 = $20, then 5% of the remainder)
                } else {
                	return (70 + ((amount - 2000) * 0.1));	// rate beyond second $1,000 is 10% (2% of the first $1,000 = $20, 5% of the second $1,000 = $50, then 10% of the remainder)
                }
                */
            default:
                return amount * 0.001; // hoping there's no other type of account, but just assume similar to Checking
        }
    }

    public double sumTransactions() {
    	double total = 0.0;
    	
    	if (checkIfTransactionsExist(true)) {
    		for (Transaction t : transactions) {
    			total += t.amount;
    		}
    	}
    	
   		return total;
   	}

    private boolean checkIfTransactionsExist(boolean checkAll) {
    	return (transactions.size() > 0);	// checks if account has any transactions listed
    }
    
    int getLatestTransactionIndex(String transactionType) {
    	if (checkIfTransactionsExist(true)) {
    		// initialise indexes at -1 (invalid values)
    		int last_transaction_index = -1;
	    	int last_deposit_index = -1;	
	    	int last_withdrawal_index = -1;	
	    	
    		for (Transaction t : transactions) {
    			last_transaction_index++;	// index of transaction in list, increments per transaction
    			if (t.amount < 0) {
    				last_withdrawal_index = last_transaction_index;	// if it's a withdrawal, update the index
    			} else {
    				last_deposit_index = last_transaction_index;
    			}
    		}
    		
    		switch (transactionType) {
	    		case "transaction":	// any kind of transaction
	    			return last_transaction_index;	// could also have been (transactions.size() - 1)
	    		case "deposit":		// deposits
	    			return last_deposit_index;
	    		case "withdrawal":	// withdrawals
	    			return last_withdrawal_index;
    			default:
    				throw new IllegalArgumentException("transaction type not recognised");
    		}
    		// if last_withdrawal_index = -1, no withdrawals made at all from this account
    		// if else (>= 0), returns the index of the withdrawal in the transaction list
    	} else {
    		return -1;		// no transactions made at all with this account
    	}
    }
    
    public int daysSinceTransaction(Transaction t, Date dateInQuestion) {
    	Date dateOfTransaction = t.getTransactionDate();
    	
    	if (dateOfTransaction.compareTo(new Date()) > 0) {
    		throw new IllegalArgumentException("date of transaction cannot be later than today");
    	} else if (dateOfTransaction.compareTo(dateInQuestion) > 0) {
    		throw new IllegalArgumentException("transaction has taken place after the date in question");
    	} else {
	    	long millisecondSpan = dateInQuestion.getTime() - dateOfTransaction.getTime();
	    	long daySpan = millisecondSpan / t.getDateProvider().DAYS;
	    	return ((int) daySpan);	// cast long to int to avoid truncation of value
    	}
    }

    public int getAccountType() {
        return accountType;
    }

}
