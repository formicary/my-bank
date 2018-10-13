package com.abc;

/**
 * 
 * This enum holds all account types for convenience 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public enum AccountEnum {
	SAVINGSACCOUNT("SAVINGS ACCOUNT"), CHECKINGACCOUNT("CHECKING ACCOUNT"), MAXISAVINGACCOUNT("MAXISAVINGACCOUNT"); 
	
	private String type;
	
	/**
	 * Constructor
	 * @param type the type of account
	 */
	private AccountEnum(String type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return the type of account
	 */
	public String getType() {
		return type;
	}

}
