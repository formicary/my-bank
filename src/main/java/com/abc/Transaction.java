package com.abc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This class represent a simple record of a transaction. It stores amount and time of the transaction.
 * If amount is positive than transaction is a deposit, if negative than a withdrawal. 
 */
public class Transaction implements Cloneable {
    
	/**
	 * Amount of the transaction.
	 */
	final private BigDecimal amount;
	/**
	 * Transaction date.
	 */
	final private Date transactionDate;

	/**
	 * Constructor of the <code>Transaction</code> class. 
	 * @param amount.
	 */
    public Transaction(final BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.INSTANCE.now();
    }
    
    private Transaction(final BigDecimal amount, final Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

	/**
	 * @return the amount of the transaction.
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	
	/**
	 * @return date of the transaction.
	 */
	public Date getTransactionDate() {
		return (Date)transactionDate.clone();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Transaction(amount, getTransactionDate());
	}

}
