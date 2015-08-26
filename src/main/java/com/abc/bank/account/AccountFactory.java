package com.abc.bank.account;

import java.util.concurrent.atomic.AtomicInteger;

public enum AccountFactory {
	INSTANCE;
	private AtomicInteger nextAccNo = new AtomicInteger(initAccountSeq());
	
	public Account getAccount(AccountType type){
		Integer accNo = nextAccNo.getAndIncrement();
		return new Account(accNo,type);
//		switch (type){
//		case CHEQUING:
//			return new ChequingAccount(accNo);	
//		case SAVINGS:
//			return new SavingAccount(accNo);
//		case MAXI:
//			return new MaxiAccount(accNo);
//		default: 
//			throw new IllegalArgumentException("Account "+type+" not implemented");
//		}
	}

	private int initAccountSeq() {
		return 0;
	}
}
