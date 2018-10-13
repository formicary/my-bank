package com.abc;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class Deposit extends AbstractTransaction{

	public Deposit(double amount) {
		super(amount, true);
		this.transactionType = "Deposit";
	}
}
