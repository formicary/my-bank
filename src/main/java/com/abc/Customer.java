package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public Customer openAccount(Account account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
        return this;
    }

    public void transferBetweenAccounts(Account accountFrom, Account accountTo, double amount) {
        if (accountFrom == null) throw new NullPointerException("Account " + accountFrom + " doesn't exist");
        if (accountTo == null) throw new NullPointerException("Account " + accountTo + "  doesn't exist");
        if (!accounts.contains(accountFrom)) throw new IllegalArgumentException("Account " + accountFrom + " is not " + name +"' account");
        if (!accounts.contains(accountTo)) throw new IllegalArgumentException("Account " + accountFrom + " is not " + name +"' account");
        accountFrom.withdraw(amount, true);
        accountTo.deposit(amount);
    }

    public double totalInterestEarned(Boolean isShowForTheWholeYear) {
        double total = 0;
        for (Account account : accounts)
            total += account.interestEarned(isShowForTheWholeYear);
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account account : accounts) {
            statement += "\n" + statementForAccount(account) + "\n";
            total += account.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account account) {
        String statement = "";

       //Translate to pretty account type
        switch(account.getAccountType()){
            case Account.CHECKING:
                statement += "Checking Account\n";
                break;
            case Account.SAVINGS:
                statement += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                statement += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction transaction : account.getAllTransactions()) {
            switch(transaction.getTransactionType()) {
                case Transaction.DEPOSIT:
                    statement += "  " + "deposit";
                    break;
                case Transaction.WITHDRAW:
                    statement += "  " + "withdraw";
                    break;
                case Transaction.TRANSFER:
                    statement += "  " + "transfer";
                    break;
            }
            statement += " " + toDollars(transaction.getTransactionAmount()) + "\n";
            total += transaction.getTransactionAmount();
        }
        statement += "Total " + toDollars(total);
        return statement;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public String getName() {
        return name;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public List<Account> getAllCustomerAccounts() {
        return this.accounts;
    }
}
