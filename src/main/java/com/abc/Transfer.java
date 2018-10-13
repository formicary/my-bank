package com.abc;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class Transfer extends AbstractTransaction{
	
	public Transfer(double amount, boolean inFlow) {
		super(amount,inFlow);
		this.transactionType = "Transfer";
	}
}
