package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class Customer {
	private String name;
	private Map<Account, Integer> accounts;


	public Customer(String name, Bank bank, Account account) {
		this.name = name;
		this.accounts = new LinkedHashMap<Account, Integer>();
		openAccount(account, bank);
	}

	public String getName() {
		return name;
	}

	public void openAccount(Account account, Bank bank) {
		int id = account.getAccountId();
		accounts.put(account, id);
		bank.addCustomer(this);
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public BigDecimal totalInterestEarned() {
		BigDecimal total = new BigDecimal("0.00");
		for (Account tempAccount : accounts.keySet())
			total = total.add(tempAccount.interestEarnedPerAnnum());
		return total;
	}

	public String getStatement() {
		String statement = "Statement for " + name + "\n";
		BigDecimal total = new BigDecimal("0.00");
		for (Account tempAccount : accounts.keySet()) {
			statement += "\n" + statementForAccount(tempAccount) + "\n";
			total = tempAccount.returnAccountBalance().add(total);
		}
		statement += "\nTotal In All Accounts " + toDollars(total);
		return statement;
	}

	private String statementForAccount(Account account) {
		String statement = "";

		switch (account.getAccountType()) {
		case Account.CHECKING:
			statement += "Checking Account with ID: " + account.getAccountId() + "\n";
			break;
		case Account.SAVINGS:
			statement += "Savings Account with ID: " + account.getAccountId() + "\n";
			break;
		case Account.MAXI_SAVINGS:
			statement += "Maxi Savings Account with ID: " + account.getAccountId() + "\n";
			break;
		}

		BigDecimal total = new BigDecimal("0.00");
		for (Transaction tempTransaction : account.getTransactions()) {
			statement += "  "
					+ (tempTransaction.getAmount().compareTo(new BigDecimal("0.00")) == -1 ? "withdrawal" : "deposit")
					+ " " + toDollars(tempTransaction.getAmount()) + "\n";
			total = total.add(tempTransaction.getAmount());
		}
		statement += "Total " + toDollars(total);
		return statement;
	}

	private String toDollars(BigDecimal total) {
		return String.format("$%,.2f", (total.abs()));
	}

	public Account getAccount(int id) {
		Account account = null;
		for (Account tempAccount : accounts.keySet()) {
			if (accounts.get(tempAccount) == id) {
				account = tempAccount;
			}
		}
		if (account == null) {
			throw new IllegalArgumentException("You don't have an account with that ID.");
		}
		return account;
	}

	public void withdraw(int id, BigDecimal amount) {
		try {
			Account account = getAccount(id);
			BigDecimal balance = account.returnAccountBalance();
			BigDecimal newBalance = balance.subtract(amount);
			if (newBalance.compareTo(new BigDecimal("0.00")) == -1) {
				throw new IllegalArgumentException("You don't have the funds in the account for this transaction");
			} else {
				account.accountWithdraw(amount.setScale(2, RoundingMode.HALF_EVEN));
			}

		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	public void deposit(int id, BigDecimal amount) {
		try {
			Account account = getAccount(id);
			BigDecimal balance = account.returnAccountBalance();
			BigDecimal newBalance = balance.add(amount);
			if (newBalance.compareTo(new BigDecimal(Integer.MAX_VALUE / 2)) == 1) {
				throw new IllegalArgumentException(
						"That amount is too large for one account, limit is" + " $" + (Integer.MAX_VALUE / 2) + ".00");
			} else {
				account.accountDeposit(amount.setScale(2, RoundingMode.HALF_EVEN));
			}
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	public void transferToAccount(int id1, int id2, BigDecimal amount) {
		try {
			Account account1 = getAccount(id1);
			Account account2 = getAccount(id2);

			account1.accountWithdraw(amount);
			account2.accountDeposit(amount);

		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

}
