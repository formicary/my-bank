package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import com.abc.banking.exceptions.TransactionException;

public interface Account {
	
	Transaction deposit(BigDecimal amount);

	Transaction withdraw(BigDecimal amount) throws TransactionException;
	
	public void rollBackTransactionIfExists(Transaction transaction);

	public void ensureInterestAccrued();
	
	public Collection<Transaction> getTransactions();

	public BigDecimal getBalance();
	
	public String getAccountTypeName();
	
	public BigDecimal getDailyInterest(LocalDate day);
}