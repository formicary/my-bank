package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

/**
 * The Class Account.
 */
public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    
    public List<Transaction> transactions;

    /**
     * Instantiates a new account.
     *
     * @param accountType the account type
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Deposit specified amount into this account.
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

	/**
	 * Withdraw specified amount from this account.
	 *
	 * @param amount the amount to withdraw
	 */
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } 
	    else if (amount > this.sumTransactions()) {
	    	throw new IllegalArgumentException("amount must be less than current balance");
	    }
	    else {
	        transactions.add(new Transaction(-amount));
	    }
	}

    /**
     * Interest earned based on account type, account balance and date since last withdrawal.
     *
     * @return the interest earned on account balance
     */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
        	case CHECKING:
        		return amount * calculateAccruedIntrestRate(0.001);
        		
            case SAVINGS:
                if (amount <= 1000)
                    return amount * calculateAccruedIntrestRate(0.001);
                else
                    return 1 + (amount-1000) * calculateAccruedIntrestRate(0.002);
                
            case MAXI_SAVINGS:
                if (checkNoWithdrawlsWithinTenDays())
                	return amount * calculateAccruedIntrestRate(0.005);
                else
                	return amount * calculateAccruedIntrestRate(0.001);
                
            default:
            	throw new IllegalArgumentException("Error, invalid account type");
        }
    }

    /**
     * Sums the accounts transactions.
     *
     * @return the double
     */
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    /**
     * Gets the account type.
     *
     * @return the account type
     */
    public int getAccountType() {
        return accountType;
    }
    
    /**
     * Check no withdrawals from account within ten days.
     *
     * @return true, if no withdrawals
     */
    @VisibleForTesting()
    protected boolean checkNoWithdrawlsWithinTenDays() {
    	Long tenDaysInMs = (long) 8.64e+8;
    	
    	if(transactions.isEmpty())
    		return false;
    	
    	Date dateNow = DateProvider.getInstance().now(); 
    	Date lastWithdrawlDate = transactions.get(transactions.size()-1).getTransactionDate();
    	
    	Long timeBetweenLastWithdrawl = Math.abs(dateNow.getTime() - lastWithdrawlDate.getTime());
    	
    	if(timeBetweenLastWithdrawl > tenDaysInMs)
    		return true;
    	else
    		return false;
    }

    /**
     * Calculate the accrued interest rate over a year long period.
     * The interest rate is calculated from the accounts first transaction.
     *
     * @param annualInterestRate the account type annual interest rate
     * @return the accrued interest rate 
     */
    @VisibleForTesting()
    protected double calculateAccruedIntrestRate(double annualInterestRate) {
    	Long DayInMs = (long) 8.64e+7;
    	
    	double dailyIntrestRate = annualInterestRate / 365;
    	
    	Date dateNow = DateProvider.getInstance().now(); 
    	Date firstTransactionDate = transactions.get(0).getTransactionDate();
    	
    	Long timeAccountHasBeenOpenMs = Math.abs(dateNow.getTime() - firstTransactionDate.getTime());
    	int daysAccountHasBeenOpen = (int) (timeAccountHasBeenOpenMs / DayInMs);
    	
    	return
    		dailyIntrestRate*(daysAccountHasBeenOpen%365);
    }
}
