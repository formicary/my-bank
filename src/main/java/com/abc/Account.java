package com.abc;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

	private double balance;
	public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    protected int days = 365;

    protected int accountType;
    protected double interestRate;
    public List<Transaction> transactions = new ArrayList<Transaction>();

  
   public List<Transaction> getTransactions() {
		return transactions;
	}

public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount,true));
			balance += amount;
			}
	}

   public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else if (amount>balance){
			throw new IllegalArgumentException(
					"Amount requested exceeds the balance available in account");
		}
		else {
			transactions.add(new Transaction(-amount,false));
			balance -= amount;
		}
	}

   
   public double interestEarned(Account accounts) {
		
		double currentDayBalance = 0.0;
		double interestAmount = 0.0;
		Date previousDate = null;
		Date currentTransactionDate = null;
		 
		for (Transaction t : accounts.transactions) {
			currentTransactionDate = new Date(t.getTransactionDate().getTime());
			if (previousDate == null) {
				previousDate = currentTransactionDate;
				currentDayBalance += t.amount;
			} else if (currentTransactionDate.equals(previousDate)) {
				previousDate = currentTransactionDate;
				currentDayBalance += t.amount;
			} else {
				
				interestAmount += calculateInterestForAccount(currentDayBalance,daysBetween(previousDate,currentTransactionDate) );
		        
			    previousDate = currentTransactionDate;
				currentDayBalance += t.amount;
				}
		}
		if (currentTransactionDate.equals(previousDate)) {
			
		interestAmount += calculateInterestForAccount(currentDayBalance,daysBetween(previousDate,DateProvider.getInstance().now()) );
		}

		return interestAmount;

	}
   
    private int daysBetween(Date firstTransactionDate, Date lastTransactionDate) {
		// TODO Auto-generated method stub
    	 return (int)( (lastTransactionDate.getTime() - firstTransactionDate.getTime()) / (1000 * 60 * 60 * 24));
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
    public abstract double calculateInterestForAccount(double balance, int datedifference);

  
   
    
    
}
