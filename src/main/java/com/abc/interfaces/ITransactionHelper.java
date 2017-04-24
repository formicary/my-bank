package com.abc.interfaces;

public interface ITransactionHelper {
	public int getDaysDifference(ITransaction previousTransaction, ITransaction nextTransaction);
	public boolean isWithdrawnWithin(ITransaction transaction, int period);
}
