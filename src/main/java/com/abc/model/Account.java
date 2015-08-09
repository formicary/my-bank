package com.abc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account{
	
	private final int number;
	private final AccountType accountType;
	private final List<Transaction> transactionList;

	
	
	public Account(Integer number, AccountType accountType) {
		Objects.requireNonNull(number, "Account number is required");
		Objects.requireNonNull(accountType, "Type of the account is required");
		this.number = number;
		this.accountType = accountType;
		this.transactionList = new ArrayList<Transaction>();
	}

	

	public int getNumber() {
		return number;
	}

	public Money getBalance() {
		Money money = Money.ZERO_USD;
		for(final Transaction transaction : transactionList){
			money = money.plus(transaction.getMoney());
		}
		return money;
	}
	
	
	public Money getIntrestPaid() {
		Money money = Money.ZERO_USD;
		for(final Transaction transaction : transactionList){
			if(transaction.isInterest()){
				money = money.plus(transaction.getMoney());
			}
		}
		return money;
	}


	public AccountType getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	
	public void addTransaction(Transaction transaction){
		Objects.requireNonNull(transaction);
		transactionList.add(transaction);
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (number != other.number)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Account [number=" + number + ", accountType=" + accountType + ", transactionList=" + transactionList
				+ "]";
	}	
	
}
