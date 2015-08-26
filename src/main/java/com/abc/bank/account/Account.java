package com.abc.bank.account;

import java.util.List;

import com.abc.bank.bankops.Transaction;
import com.google.common.collect.Lists;

public class Account {

	protected  final List<Transaction> transactions = Lists.newArrayList();;
 	private final Integer accNo;
	private AccountType accType;
	

	public Account(Integer accNo,AccountType accType) {
    	this.accNo = accNo;
    	this.accType = accType;
    }
     
    public List<Transaction> getTransactions() {
		return Lists.newArrayList(transactions);
	}

	public AccountType getAccType() {
		return accType;
	}

  
    public void addTransaction(Transaction transaction){
    	transactions.add(transaction);
    }

	public Integer getAccNo() {
		return accNo;
	}
	
}	
