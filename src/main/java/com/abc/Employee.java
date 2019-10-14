package com.abc;

import java.math.BigDecimal;

public abstract class Employee {

	private String name;
	private Bank bank;

	public Employee(String name) {
		this.name = name;
	}

	// Checks if the teller is an employee of the bank
	public String notAnEmployee() {
		return this.getName() + " does not belong to this bank.";
	}

	// Converts monetary value to dollars
	public String toDollars(BigDecimal dollars) {
		return String.format("$%,.2f", dollars.abs());
	}

	public String getName() {
		return name;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

}
