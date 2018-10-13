package com.abc;

import java.util.Date;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class AbstractTransaction implements Transaction{
	
	private final double amount;
    private final Date transactionDate;
    protected String transactionType;
    protected boolean inFlow; //true if the amount if flowing in to the account
    
    /**
     * Constructor
     * @param amount the amount involved in the transaction
     */
	public AbstractTransaction(double amount, boolean inFlow) {
		this.inFlow = inFlow;
		if(inFlow) {
			this.amount = amount;
		}else {
			this.amount = -amount;
		}
		this.transactionDate = DateProvider.getInstance().now();
	}
	
	/**
	 * 
	 * @return the amount involved in the transaction
	 */
	public double getAmount() {
		return amount;
	}
    
	/**
	 * 
	 * @return the type of transaction
	 */
	public String getType() {
		return transactionType;
	}
	
	/**
	 * 
	 * @return the date when this transaction was carried out 
	 */
	public Date getDate() {
		return transactionDate;
	}

	/**
	 * @see com.abc.Transaction
	 */
	public boolean getFlowDirection() {
		return inFlow;
	}

}
