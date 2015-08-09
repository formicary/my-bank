package com.abc.model;

import java.util.Objects;

public class CustomerTransactionRequest {

	private final Customer customer;
	private final int accountNumberFrom;
	private final int accountNumberTo;
	private final Money amount;
	
	
	public CustomerTransactionRequest(Customer customer, int accountNumberFrom, int accountNumberTo, Money amount) {
		Objects.requireNonNull(customer);
		Objects.requireNonNull(amount);
		this.customer = customer;
		this.accountNumberFrom = accountNumberFrom;
		this.accountNumberTo = accountNumberTo;
		this.amount = amount;
	}


	public Customer getCustomer() {
		return customer;
	}


	public int getAccountNumberFrom() {
		return accountNumberFrom;
	}


	public int getAccountNumberTo() {
		return accountNumberTo;
	}


	public Money getAmount() {
		return amount;
	}
	
	
}
