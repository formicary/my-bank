package com.abc.bank.bankops;

import java.util.concurrent.atomic.AtomicLong;

import com.abc.bank.utils.DateProvider;

public enum TransactionBuilder {
	INSTANCE;
	
	private static AtomicLong transactionId = new AtomicLong(initTransactionSeq());
	
	/**
	 * Creates the transaction.
	 *
	 * @param transactionAmount the transaction amount
	 * @param transType the trans type
	 * @return the transaction
	 */
	public Transaction createTransaction(Double transactionAmount,TransactionType transType){
		return new Transaction(transactionId.incrementAndGet(),
				DateProvider.INSTANCE.today(),transactionAmount,transType);
	}

	private static Long initTransactionSeq() {
		//retrieve next transactionSeq from persistence store
		return  0L;
	}
}
