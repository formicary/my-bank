package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }
    
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public String getName() {
        return name;
    }
    
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    public String getCustomerSummary() {
    	return this.getName() + " (" + format(this.getNumberOfAccounts(), "account") + ")";
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
    	return number + " " + (number == 1 ? word : word + "s");
    }
    
    public void transferBetweenAccounts(int amount, Account accountFrom, Account accountTo){
    	if (amount <= 0) {
    		throw new IllegalArgumentException("Transfer amount must be greater than zero");
    	} else {
    		accountFrom.withdraw(amount);
    		accountTo.deposit(amount);
    	}
    }
    
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + Bank.toDollars(total);
        return statement;
    }
    
    private String statementForAccount(Account a) {
        String s = "";

       //Translate to account type string
        switch(a.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi-Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + Bank.toDollars(t.getAmount()) + " - " + (t.getTransactionDate()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + Bank.toDollars(total);
        return s;
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }


    
}
