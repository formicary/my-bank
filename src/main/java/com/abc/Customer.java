package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer {
	private String name;
	private List<Account> accountList;
	private static ArrayList<Integer> accountIDList = new ArrayList<Integer>();

	public Customer(String name) {
		this.name = name;
		this.accountList = new ArrayList<Account>();
	}

	public String openAccount(String accountType) {

		switch (accountType.toUpperCase()) {

		case Constants.CHECKING_ACCOUNT:
			addToAccountList(new Checking_Account(accountType.toUpperCase(), generateAccountID()));
			System.out.println(Constants.CHECKING_ACCOUNT_SUCCESSFULLY_CREATED);
			return Constants.CHECKING_ACCOUNT_SUCCESSFULLY_CREATED;

		case Constants.SAVINGS_ACCOUNT:
			addToAccountList(new Savings_Account(accountType.toUpperCase(), generateAccountID()));
			System.out.println(Constants.SAVINGS_ACCOUNT_SUCCESSFULLY_CREATED);
			return Constants.SAVINGS_ACCOUNT_SUCCESSFULLY_CREATED;

		case Constants.MAXISAVINGS_ACCOUNT:
			addToAccountList(new MaxiSavings_Account(accountType.toUpperCase(), generateAccountID()));
			System.out.println(Constants.MAXISAVINGS_ACCOUNT_SUCCESSFULLY_CREATED);
			return Constants.MAXISAVINGS_ACCOUNT_SUCCESSFULLY_CREATED;

		default:
			System.out.println(Constants.DEFAULT_CREATE_ACCOUNT_MESSAGE);
			return Constants.DEFAULT_CREATE_ACCOUNT_MESSAGE;
		}
	}

	public int generateAccountID() {
		int accountID;
		if (accountIDList != null) {
			accountID = accountIDList.size() + Constants.ACCOUNT_ID_DENOMINATIONS;
			addAccountNumberToList(accountID);
			return accountID;

		} else {
			accountID = Constants.ACCOUNT_ID_DENOMINATIONS;
			addAccountNumberToList(accountID);
			return accountID;
		}
	}

	public void addAccountNumberToList(int accountID) {
		getAccountIDList().add(accountID);
	}

	public void addToAccountList(Account newAccount) {
		accountList.add(newAccount);
	}

	public int getNumberOfAccounts() {
		return accountList.size();
	}

	public String deposit(int accountID, BigDecimal amount) {
		return accountFinder(accountID).deposit(amount);
	}

	public String withdrawal(int accountID, BigDecimal amount) {
		return accountFinder(accountID).withdraw(amount);
	}

	public BigDecimal totalInterestEarned() {
		BigDecimal totalInterestEarned = Constants.ZERO_BD;
		for (Account a : accountList)
			totalInterestEarned = totalInterestEarned.add(a.interestEarned());
		return totalInterestEarned.setScale(2, RoundingMode.HALF_UP);
	}

	public String getStatement() {
		if (getAccountList().isEmpty() == Constants.TRUE) {
			System.out.println(Constants.EMPTY_STATEMENT);
			return Constants.EMPTY_STATEMENT;
		} else {
			String statement = Constants.STATEMENT_TITLE;
			for (Account a : accountList) {
				String statementBody = Constants.ACCOUNT_ID + a.getAccountID() + Constants.ACCOUNT_BALANCE
						+ a.getBalance() + generateDepositStatement(a) + generateWithdrawalStatement(a);

				statement += statementBody;
			}
			System.out.println(statement + Constants.END_OF_STATEMENT);
			return statement + Constants.END_OF_STATEMENT;
		}
	}

	public String generateDepositStatement(Account account) {
		if (account.getDepositList().isEmpty() == Constants.TRUE) {
			return Constants.NO_DEPOSITS;
		} else {
			String depositStatement = Constants.DEPOSITS_TITLE;
			Iterator<Deposit> itrD = account.getDepositList().iterator();
			while (itrD.hasNext()) {
				Deposit d = (Deposit) itrD.next();
				depositStatement += d.toString();
			}
			System.out.println(depositStatement);
			return depositStatement;
		}
	}

	public String generateWithdrawalStatement(Account account) {
		if (account.getWithdrawalList().isEmpty() == Constants.TRUE) {
			return Constants.NO_WITHDRAWALS;
		} else {
			String withdrawalStatement = Constants.WITHDRAWALS_TITLE;
			Iterator<Withdrawal> itrW = account.getWithdrawalList().iterator();
			while (itrW.hasNext()) {
				Withdrawal w = (Withdrawal) itrW.next();
				withdrawalStatement += w.toString();
			}
			return withdrawalStatement;
		}
	}

	public String transferBetweenAccounts(int accountIDFrom, int accountIDTo, BigDecimal amount) {
		if (accountFinder(accountIDFrom).getBalance().compareTo(amount) == -1) {
			return Constants.INSUFFICIENT_TRANSFER_FUNDS;
		} else {
			withdrawal(accountIDFrom, amount);
			deposit(accountIDTo, amount);
			return Constants.SUCCESSFUL_MONEY_TRANSFER + accountIDFrom + Constants.TO_ACCOUNT + accountIDTo;
		}
	}

	public Account accountFinder(int accountID) {
		return (getAccountList().stream().filter(i -> i.getAccountID() == accountID).findAny().orElse(null));
	}

	public List<Account> getAccounts() {
		return accountList;
	}

	public void setAccounts(List<Account> accounts) {
		this.accountList = accounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

	public static ArrayList<Integer> getAccountIDList() {
		return accountIDList;
	}

	public static void setAccountIDList(ArrayList<Integer> accountIDList) {
		Customer.accountIDList = accountIDList;
	}
}
