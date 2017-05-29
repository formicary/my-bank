package com.abc.transaction;

import java.util.Date;



public interface TransactionI {
	public double getAmount();

	public Date getTransactionDate() ;

	public void setTransactionDate(Date transactionDate) ;

}
