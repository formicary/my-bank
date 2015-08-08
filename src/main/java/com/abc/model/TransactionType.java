package com.abc.model;

public enum TransactionType {
	
	WITHDRAWAL("withdrawal"),
	INTEREST("interest"),
	DEPOSIT("deposit");
	
	private final String name;
	
	private TransactionType(String name){
		this.name = name;
	}
	
	
	public String getName(){
		return name;
	}
	
	
}
