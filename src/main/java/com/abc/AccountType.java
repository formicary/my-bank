package com.abc;

public enum AccountType {
	CHECKING(0),
	SAVINGS(1),
	MAXI_SAVINGS(2);
	
	   private int value;
	   private AccountType(int value) {
	      this.value = value;
	   }
	   public int getValue() {
	      return value;
	   }
}

