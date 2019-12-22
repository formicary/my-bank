package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public int getAccountIndex(Account account){
        return accounts.indexOf(account);
    }

    //Transfer by withdrawing from one and depositing to the other account
    public void transferBetweenAccounts(int withdrawAccountIndex, int depositAccountIndex, double amount, Date date) throws Exception{

        if(withdrawAccountIndex >= accounts.size()){
            throw new Exception("Invalid withdraw account index");
        }

        if(depositAccountIndex >= accounts.size()){
            throw new Exception("Invalid deposit account index");
        }

        Account withdrawAccount = accounts.get(withdrawAccountIndex);
        Account depositAccount = accounts.get(depositAccountIndex);

        withdrawAccount.withdraw(amount, date);
        depositAccount.deposit(amount, date);
    }

    //Get teh total earned interest across all the accounts
    public double totalCompoundInterestEarned(Date date) {
        double total = 0;
        for (Account a : accounts)
            total += a.getCompoundInterest(date);
        return total;
    }

    //Print a statement where the total is the sum of the balances of the accounts which includes the earned interest
    public String getStatement() {
        double total = 0.0;
        StringBuilder statementBuilder = new StringBuilder("Statement for " + name + "\n");

        for (Account a : accounts) {
            statementBuilder.append("\n").append(statementForAccount(a)).append("\n");
            total += a.getBalance(DateProvider.getInstance().now());
        }

        statementBuilder.append("\nTotal In All Accounts ").append(Utils.toDollars(total));

        return statementBuilder.toString();
    }

    //Print information about a a specific account
    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                s.append("Checking Account\n");
                break;
            case SAVINGS:
                s.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        List<Transaction> transactions = a.getTransactions();
        for (Transaction t : transactions) {
            s.append("  ").append(t.getAmount() < 0 ? "withdrawal" : "deposit").append(" ").append(Utils.toDollars(t.getAmount())).append("\n");
            total += t.getAmount();
        }
        s.append("Total transactions amount: ").append(Utils.toDollars(total)).append("\n");
        s.append("Balance: ").append(Utils.toDollars(a.getBalance(DateProvider.getInstance().now())));

        return s.toString();
    }
}
