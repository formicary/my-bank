package com.abc.account;

public class NegativeAmountException extends RuntimeException {
	
	NegativeAmountException(String message){
		super (message);
	}
}
