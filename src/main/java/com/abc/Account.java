package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.text.WordUtils;

import com.abc.Transaction.TRANSACTION_TYPE;

public class Account {
    public enum ACCOUNT_TYPE {
        CHECKING, SAVINGS, MAXI_SAVINGS;
    	
    	public String toString() {
    		return WordUtils.capitalizeFully(name().replace("_", " "));
    	}
    }
    
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    volatile boolean isStopIssued;
    

	private final ACCOUNT_TYPE accountType;
    private List<Transaction> transactions;
    private BigDecimal balance;
    private BigDecimal accruedInterest;

	public Account(ACCOUNT_TYPE accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = BigDecimal.ZERO;
        this.accruedInterest = BigDecimal.ZERO;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
        	BigDecimal amount2dp = amount.setScale(2, RoundingMode.FLOOR);
        	addTransaction(new Transaction(amount2dp, TRANSACTION_TYPE.DEPOSIT));
        	setBalance(balance.add(amount2dp));
        }
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else if (balance.compareTo(amount) < 0){
        	throw new IllegalArgumentException("Account balance must be greater or equal to the withdrawal amount");
        } else {
        	amount = amount.negate();
        	BigDecimal amount2dp = amount.setScale(2, RoundingMode.FLOOR);
        	addTransaction(new Transaction(amount2dp, TRANSACTION_TYPE.WITHDRAWAL));
        	setBalance(balance.add(amount2dp));
        }
    }

    public void accrueInterest() {
    	if (balance.compareTo(BigDecimal.ZERO) == 1) {
	    	BigDecimal interest = BigDecimal.ZERO;
	        if (accountType == ACCOUNT_TYPE.SAVINGS) {
	        	if (balance.compareTo(new BigDecimal("1000")) < 1) {
	        		interest = balance.multiply(new BigDecimal("0.001"));
	        	} else {
	        		interest = interest.add(BigDecimal.valueOf(1000 * 0.001));
	        		BigDecimal remainingBalance = balance.subtract(new BigDecimal("1000"));
	        		interest = interest.add(remainingBalance.multiply(new BigDecimal("0.002")));
	        	}
	        } else if (accountType == ACCOUNT_TYPE.MAXI_SAVINGS) {
	        	if (!hasWithdrawalsWithinLast10Days()) {
	        		interest = balance.multiply(new BigDecimal("0.05"));
	        	} else {
	        		interest = balance.multiply(new BigDecimal("0.001"));
	        	}
	        } else {
	        	interest = balance.multiply(new BigDecimal("0.001"));
	        }
	        
	        compoundInterest(interest);
	        setBalance(balance.add(interest));
    	}
    }
    
    private Boolean hasWithdrawalsWithinLast10Days() {
    	for (Transaction transaction: transactions) {
    		if (transaction.getTransactionType() == TRANSACTION_TYPE.WITHDRAWAL && transaction.withinLast10Days()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
    public void addTransaction(Transaction transaction) {
    	this.transactions.add(transaction);
    }

	public ACCOUNT_TYPE getAccountType() {
		return accountType;
	}
    
    public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public BigDecimal getAccruedInterest() {
		return accruedInterest;
	}

	public void setAccruedInterest(BigDecimal accruedInterest) {
		this.accruedInterest = accruedInterest;
	}
	
	public void compoundInterest(BigDecimal interest) {
		this.accruedInterest = accruedInterest.add(interest);
	}
}
