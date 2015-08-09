package com.abc.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Transaction{
	
	private final TransactionType type;
    private final Money money;
    private final Date transactionDate;

    public Transaction(Date date, TransactionType type, final BigDecimal amount) {
	    this(date, type, new Money(amount));	
    }
    
    public Transaction(Date date, TransactionType type, final Money amount) {
    	Objects.requireNonNull(date);
    	Objects.requireNonNull(type);
    	Objects.requireNonNull(amount);
    	
    	this.type = type;
        this.money = amount;
        this.transactionDate = date;
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

	@Override
	public String toString() {
		return "Transaction [type=" + type + ", money=" + money + ", transactionDate=" + transactionDate + "]";
	}
}
