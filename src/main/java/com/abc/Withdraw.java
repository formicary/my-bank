package com.abc;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class Withdraw extends AbstractTransaction{

	public Withdraw(double amount) {
		super(amount,false);
		this.transactionType = "Withdraw";
	}

}
