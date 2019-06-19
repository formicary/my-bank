package main.java.com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

import java.text.ParseException;

public class Customer {
    private String name;
    private List<Account> accounts;
    ////////
    private String statement;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.getInterestEarned();
        return total;
    }
    
    public double totalInterestEarned(int numberOfDays) throws ParseException {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(numberOfDays);
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    
    private String statementForAccount(Account a) {
        return a.getAccountStatement();
    }
    
    public void transferFunds(Account from, Account to, double amount) throws AccountException {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	    	if (accounts.contains(from) && accounts.contains(to)) {
	    		from.withdraw(amount);
	    		to.deposit(amount);
	    	} else {
	    		throw new AccountException("There is an error with the accounts");
	    	}
	    }
    }
    
//    private String statementForAccount(Account a) {
//        String s = "";
//
//       //Translate to pretty account type
//        switch(a.getAccountType()){
//            case Account.CHECKING:
//                s += "Checking Account\n";
//                break;
//            case Account.SAVINGS:
//                s += "Savings Account\n";
//                break;
//            case Account.MAXI_SAVINGS:
//                s += "Maxi Savings Account\n";
//                break;
//        }
//
//        //Now total up all the transactions
//        double total = 0.0;
//        for (Transaction t : a.transactions) {
//            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
//            total += t.amount;
//        }
//        s += "Total " + toDollars(total);
//        return s;
//    }

    
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
