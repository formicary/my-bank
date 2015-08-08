package com.abc.model;


public class AccountBuilder {
	
	private Account account;
	

	public static AccountBuilder createMaxiSaving(){
		AccountBuilder builder = new AccountBuilder();
		builder.account = new Account(AccountType.MAXI_SAVINGS);
		return builder;
	}

	
	public static AccountBuilder createSaving(){
		AccountBuilder builder = new AccountBuilder();
		builder.account = new Account(AccountType.SAVINGS);
		return builder;
	}
	
	public static AccountBuilder createChecking(){
		AccountBuilder builder = new AccountBuilder();
		builder.account = new Account(AccountType.CHECKING);
		return builder;
	}
	
	
	public AccountBuilder withWithdrawal(String amount){
		Money money = new Money(amount);
		Transaction t = new Transaction(TransactionType.WITHDRAWAL, money.negate());
		account.addTransaction(t);
		return this;
	}
	
	
	public AccountBuilder withInterest(String amount){
		Money money = new Money(amount);
		Transaction t = new Transaction(TransactionType.INTEREST,  money);
		account.addTransaction(t);
		return this;
	}
	
	
	public AccountBuilder withDeposit(String amount){
		Money money = new Money(amount);
		Transaction t = new Transaction(TransactionType.DEPOSIT,  money);
		account.addTransaction(t);
		return this;
	}
	
	public Account get(){
		return account;
	}
}
