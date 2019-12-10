package com.abc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
    private static int uniqueIdCounter = 0; //uniqueId of the next account that will be created
    
    private final int accountType;
    public List<Transaction> transactions;
    
    private int uniqueId;
    
    /*
     * Constructs object with a uniqueId and increments uniqueId counter
     * so no two accounts change have the same uniqueId
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.uniqueId = uniqueIdCounter;
        uniqueIdCounter++;
    }
    
    public int getUniqueId() {
    	return this.uniqueId;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }
    
    /**
     * @param Account to transfer to "a", the double amount to transfer
     * @return void
     * Performs transfer from this account to another by withdrawing "amount" from 
     * this account and depositing "amount" into the other account
     * as long as "amount" is positive
     **/
    public void transferTo(Account a, double amount) { // make sure user owns that account?
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        else {
            withdraw(amount);
            a.deposit(amount);
        }
    }
    
    /**
     * @param void
     * @return double the interest calculated depending on the type of account
     * For savings account:
     * 	0.1% annual flat rate but calculated and used as a 0.1/365 % daily flat rate
     * For maxi savings account:
     * 	5% annual flat rate if there have been no withdrawals within last 10 days otherwise 0.1% annual
     * 	but calculated and used as a 0.1/365 % daily flat rate
     * For checking account (default case):
     * 	0.1% annual rate calculated and used as 0.1/365 % daily rate
     */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * (0.001/365); //changed to daily interest rate from 0.1%
                else
                    return 1 + (amount-1000) * (0.002/365); //changed to daily interest rate from 0.1%
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	LocalDate currentDate = LocalDate.now();
            	Date d1 = Date.from(currentDate.minusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant());
            	
            	//checking if there are any withdrawals within past 10 days
            	boolean recentWithdrawal = false;
            	for(Transaction t: transactions) {
            		if(t.amount < 0 && t.getDate().after(d1)) {
            			recentWithdrawal = true;
            		}
            	}
            	
            	return recentWithdrawal ? amount * (0.001/365) : amount * (0.05/365); //changed to daily interest rate from 0.1% and 5% respectively
            	
            	 
            default:
                return amount * (0.001/365);
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
    
    /**
     * @param Account "a" to equality with this account
     * @return boolean if the two account are equal
     * Checks if this account and "a" have same uniqueId value
     **/
    public boolean equals(Account a) {
    	return a.getUniqueId() == this.getUniqueId();
    }
}
