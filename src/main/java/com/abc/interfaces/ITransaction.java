package com.abc.interfaces;

import org.joda.time.LocalDate;

public interface ITransaction {
	public double getAmount();
	public LocalDate getTransactionDate();
	public ITransaction getNextTransaction();
	public void setNextTransaction(ITransaction transaction);
}
