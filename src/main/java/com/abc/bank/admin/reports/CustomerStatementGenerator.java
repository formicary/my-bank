package com.abc.bank.admin.reports;

import static java.lang.Math.abs;

import java.util.List;

import com.abc.bank.account.Account;
import com.abc.bank.account.AccountType;
import com.abc.bank.admin.Customer;
import com.abc.bank.bankops.Transaction;
import com.abc.bank.bankops.TransactionType;

public class CustomerStatementGenerator extends ReportGenerator<Account, String> {

	private static final String CHECKING_NAME= "Checking Account";
	private static final String SAVING_NAME= "Savings Account";
	private static final String MAXI_NAME= "Maxi Savings Account";
	private static final String DEPOSIT_NAME= "deposit ";
	private static final String WITHDRAWL_NAME= "withdrawl ";
	private static final String INTEREST_NAME= "interest ";
	
	public CustomerStatementGenerator(Customer c) {
		super("Statement for " + c.getName() );
	}

	public boolean visit(Account t) {
		sb.append(getAccountTypeDesc(t.getAccType())+"No."+t.getAccNo());
		sb.append(System.lineSeparator());
		sb.append(dereferenceTransaction(t.getTransactions()));
		sb.append(System.lineSeparator());
		return false;
	}

	@Override
	public String getReport() {
		return sb.toString();
	}


	private String getAccountTypeDesc(AccountType accType) {
		switch(accType){
		case CHEQUING:
			return CHECKING_NAME;
		case SAVINGS:
			return SAVING_NAME;
		case MAXI:
			return MAXI_NAME;
		default: 
			throw new IllegalArgumentException("Not Supported");
		}
	}

	private String dereferenceTransaction(List<com.abc.bank.bankops.Transaction> transactions){
		StringBuilder sb = new StringBuilder();
		double total = 0;
		for (Transaction t:transactions){
			Double amount = t.getAmount();
			sb.append("Transaction:"+t.getTransactionID() + " "+  getTransactionDesc(t.getTransactionType()) + " "+ toDollars(amount));
			total += amount;
			sb.append(System.lineSeparator());
		}
		sb.append("Total: "+ toDollars(total));
		return sb.toString();
	}

	private String getTransactionDesc(TransactionType transactionType) {
		switch (transactionType){
		case DEPOSIT: return DEPOSIT_NAME;
		case INTEREST: return INTEREST_NAME;
		case WITHDRAWL: return WITHDRAWL_NAME;
		default : throw new IllegalArgumentException("Not Supported");
		}
	}

	private String toDollars(double d){
		return String.format("$%,.2f", abs(d));
	}

}
