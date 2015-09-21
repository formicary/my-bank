package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
	
	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	
    public final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
    	this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(BigDecimal amount) {
    	if (amount.compareTo(BigDecimal.ZERO) != 1) { // more than $0 deposited
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(BigDecimal amount) {
	    if (amount.compareTo(BigDecimal.ZERO) != 1) { // more than $0 withdrawn
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(amount.negate()));
	    }
	}
	
	public void transfer(Account from, Account to, BigDecimal amount){
    	if(amount.compareTo(from.sumTransactions()) == 1){
    		from.transactions.add(new Transaction(amount.negate()));
    		to.transactions.add(new Transaction(amount));
    	}
    	else
    		throw new IllegalArgumentException("Insufficient funds");
    }

    public BigDecimal sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private BigDecimal checkIfTransactionsExist(boolean checkAll) {
        BigDecimal amount = BigDecimal.ZERO;
        for (Transaction t: transactions)
            amount = amount.add(t.amount);
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public BigDecimal interestEarned() {
        return BigDecimal.ZERO;
    }
}
