package com.abc;

import static java.lang.Math.abs;

import java.util.List;

public class CustomerStatement implements IReport {
	private final String customerName;
	private final List<Account> accounts;
	
	public CustomerStatement(String customerName, List<Account> accounts) {
		this.customerName = customerName;
		this.accounts = accounts;
	}

	public String build() {
        String statement = "Statement for " + customerName + "\n";
        double total = 0.0;
        for (Account account : accounts) {
            statement += "\n" + statementForAccount(account) + "\n";
            total += account.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
	}

    private String statementForAccount(Account account) {
    	//Translate to pretty account type
        String statement = account.getAccountName() + "\n";;

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction transaction : account.transactions) {
            statement += "  " + (transaction.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(transaction.getAmount()) + "\n";
            total += transaction.getAmount();
        }
        statement += "Total " + toDollars(total);
        return statement;
    }

    private String toDollars(double amount) {
        return String.format("$%,.2f", abs(amount));
    }
}
