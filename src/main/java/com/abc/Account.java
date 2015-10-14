package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import java.math.MathContext;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

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
            transactions.add(new Transaction(BigDecimal.valueOf(amount)));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(BigDecimal.valueOf(-amount)));
	    }
	}
	
	
    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        BigDecimal result;
        BigDecimal a;
        MathContext mc = new MathContext(4);
        switch(accountType){
        	case CHECKING:
        		a = new BigDecimal(new BigDecimal("0.001").divide(new BigDecimal("365"), mc).toString());
        		a = a.add(new BigDecimal("1"));
        		a = a.pow(365, mc);
        		a = a.multiply(amount, mc);
        		return a.subtract(amount, mc);
        	case SAVINGS:
            	if(amount.compareTo(new BigDecimal("1000")) <= 0){
            		a = new BigDecimal(new BigDecimal("0.001").divide(new BigDecimal("365"), mc).toString());
            		a = a.add(new BigDecimal("1"));
            		a = a.pow(365, mc);
            		a = a.multiply(amount, mc);
            		return a.subtract(amount, mc);
            	} else {
            		a = new BigDecimal(new BigDecimal("0.002").divide(new BigDecimal("365"), mc).toString());
            		a = a.add(new BigDecimal("1"));
            		a = a.pow(365, mc);
            		a = a.multiply(amount, mc);
            		a = a.add(new BigDecimal("1"));
                	return a.subtract(amount, mc);
            	}
            case MAXI_SAVINGS:
            	//5% if no withdrawals in last 10days, else 0.1%
            	BigDecimal b;
            	Transaction lastTransaction = transactions.get(transactions.size()-1);
            	Date dateOne = new Date();
            	Date dateTwo = lastTransaction.transactionDate;
            	long dateDiff = dateOne.getTime() - dateTwo.getTime();
            	long dayDiff = dateDiff/(24 * 60 * 60 * 1000);
            	if(dayDiff > 10){ 
            		b = new BigDecimal(new BigDecimal("0.05").divide(new BigDecimal("365"), mc).toString());
					b = b.add(new BigDecimal("1"));
            		b = b.pow(365, mc);
            		b = b.multiply(amount, mc);
            		result = amount.multiply(new BigDecimal("0.05"), mc);
            		return b.subtract(amount, mc);
            	} else {
            		boolean withdrawal = false;
            		for(int i = 1; i < transactions.size(); i++){
            			lastTransaction = transactions.get(transactions.size()-i);
            			dateTwo = lastTransaction.transactionDate;
            			dateDiff = dateOne.getTime() - dateTwo.getTime();
            			dayDiff = dayDiff = dateDiff/(24 * 60 * 60 * 1000);
            			if(dayDiff < 10){
            				if(lastTransaction.amount.compareTo(BigDecimal.ZERO) < 0){
            					withdrawal = true;
            					a = new BigDecimal(new BigDecimal("0.001").divide(new BigDecimal("10"), mc).toString());
                        		a = a.add(new BigDecimal("1"));
                        		a = a.pow(10, mc);
                        		a = a.multiply(amount, mc);
            					return a.subtract(amount, mc);
            				}
            			} else {
            				break;
            			}
            		}
            		b = new BigDecimal(new BigDecimal("0.05").divide(new BigDecimal("365"), mc).toString());
					b = b.add(new BigDecimal("1"));
            		b = b.pow(365, mc);
            		b = b.multiply(amount, mc);
            		result = amount.multiply(new BigDecimal("0.05"), mc);
            		return b.subtract(amount, mc);
            	}
        }
        return null;
    }

    public BigDecimal sumTransactions() {
        BigDecimal amount = new BigDecimal("0.0");
        for (Transaction t: transactions)
        	amount = amount.add(t.amount);
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
