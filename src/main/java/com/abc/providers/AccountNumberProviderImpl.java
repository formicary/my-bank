package com.abc.providers;

import org.springframework.stereotype.Component;


@Component
public class AccountNumberProviderImpl implements AccountNumberProvider {
	
	
	private static int accountNumber = 1;

	
	public int  getAccountNumber() {
		synchronized (this) {
			return accountNumber++;
		}
	}
	
	
	
}
