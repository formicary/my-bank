package com.abc.model;

public enum AccountType {
	
	CHECKING("Checking Account"),
	SAVINGS("Savings Account"),
	MAXI_SAVINGS("Maxi Savings Account");
	
	private final String name;
	
	private AccountType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}
