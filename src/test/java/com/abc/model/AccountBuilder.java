package com.abc.model;


import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.format.datetime.DateFormatter;


public class AccountBuilder {
	
	
	private static AtomicInteger number = new AtomicInteger();
	private final static DateFormatter FORMATTER = new DateFormatter("dd-MM-yyyy");
	private Account account;
	
	private AccountBuilder(){}
	
	public static AccountBuilder createMaxiSaving(){
		AccountBuilder builder = new AccountBuilder();
		builder.account = new Account(number.incrementAndGet(), AccountType.MAXI_SAVINGS);
		return builder;
	}

	
	public static AccountBuilder createSaving(int accountNo){
		AccountBuilder builder = new AccountBuilder();
		builder.account = new Account(accountNo, AccountType.SAVINGS);
		return builder;
	}
	
	public static AccountBuilder createSaving(){
		AccountBuilder builder = new AccountBuilder();
		builder.account = new Account(number.incrementAndGet(), AccountType.SAVINGS);
		return builder;
	}
	
	public static AccountBuilder createChecking(){
		AccountBuilder builder = new AccountBuilder();
		builder.account = new Account(number.incrementAndGet(), AccountType.CHECKING);
		return builder;
	}
	
	
	public AccountBuilder withWithdrawal(String amount){
		return createTransaction( new Money(amount).negate(), TransactionType.WITHDRAWAL,  new Date());
	}
	
	
	public AccountBuilder withWithdrawal(String amount, String date){
		return createTransaction(new Money(amount).negate(), TransactionType.WITHDRAWAL,  toDate(date) );
	}
	
	
	public AccountBuilder withInterest(String amount){
		return createTransaction(amount, TransactionType.INTEREST,  new Date());
	}
	
	
	public AccountBuilder withInterest(String amount, String date){
		return createTransaction(amount, TransactionType.INTEREST,  toDate(date));
	}
	
	
	public AccountBuilder withDeposit(String amount){
		return createTransaction(amount, TransactionType.DEPOSIT, new Date());
	}
	
	
	public AccountBuilder withDeposit(String amount, String date){
		return createTransaction(amount, TransactionType.DEPOSIT, toDate(date));
	}
	

		
	private AccountBuilder createTransaction(String amount, TransactionType type, Date date){
		return createTransaction(new Money(amount), type, date );
	}
	
	
	private AccountBuilder createTransaction(Money amount, TransactionType type, Date date){
		Transaction t = new Transaction(date, type, amount);
		account.addTransaction(t);
		return this;
	}
	
	private static Date toDate(String strDate){
		try {
			return FORMATTER.parse(strDate, Locale.UK);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format: "  + strDate);
		}
	}
	
	
	public Account get(){
		return account;
	}
}
