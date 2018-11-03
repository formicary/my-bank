package com.abc;

public enum AccountType {
	
	CHECKING("Checking Account"),
	SAVINGS("Savings Account"),
	MAXI_SAVINGS("Maxi Savings Account");
	
	private final String accountType;
	
	AccountType(final String s) {
		this.accountType = s;
	}
	
	@Override
	public String toString() {
		return accountType;
	}

}
