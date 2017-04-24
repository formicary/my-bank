package com.abc.implementation;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.abc.interfaces.ITransaction;
import com.abc.interfaces.ITransactionHelper;

public class TransactionHelper implements ITransactionHelper {

	public int getDaysDifference(ITransaction previousTransaction, ITransaction nextTransaction) 
	{
		int daysSinceLastTransaction = Days.daysBetween(previousTransaction.getTransactionDate().toDateTimeAtCurrentTime(), nextTransaction == null ? new LocalDate().toDateTimeAtCurrentTime() : nextTransaction.getTransactionDate().toDateTimeAtCurrentTime()).getDays();
    	return daysSinceLastTransaction;
	}

	public boolean isWithdrawnWithin(ITransaction transaction, int period) 
	{
		ITransaction nextTransaction = transaction.getNextTransaction();
		int daysSinceLastTransaction = Days.daysBetween(transaction.getTransactionDate().toDateTimeAtCurrentTime(), nextTransaction == null ? new LocalDate().toDateTimeAtCurrentTime() : nextTransaction.getTransactionDate().toDateTimeAtCurrentTime()).getDays();
		if(nextTransaction != null && daysSinceLastTransaction <= period && nextTransaction.getAmount() < 0) {
			return true;
		}
		return false;
	}

}
