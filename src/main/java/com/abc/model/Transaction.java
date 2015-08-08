package com.abc.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import com.abc.providers.DateProvider;

public class Transaction {
	
	private final TransactionType type;
    private final Money money;
    private final Date transactionDate;

    public Transaction(TransactionType type, final BigDecimal amount) {
	    this(type, new Money(amount));	
    }
    
    public Transaction(TransactionType type, final Money amount) {
    	Objects.requireNonNull(type);
    	Objects.requireNonNull(amount);
    	this.type = type;
        this.money = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

	public Money getMoney() {
		return money;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public TransactionType getType() {
		return type;
	}

	public boolean isWithdrawal(){
		return type == TransactionType.WITHDRAWAL;
	}
	
	public boolean isInterest(){
		return type == TransactionType.INTEREST;
	}
	
	public boolean isDeposit(){
		return type == TransactionType.DEPOSIT;
	}

    
}
