package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private static int count = 0;
    private String name;
    private List<Account> accounts;
    private int customerID;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
        this.customerID = count ++;
    }

    public String getName() {
        return name;
    }

    public int getID(){
        return customerID;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public Account getAccount(int index){
        if (index < getNumberOfAccounts()){
            return accounts.get(index);
        }
        else{
            System.out.println("Account does not exist");
            return null;
        }
    }

    public void transfer(Account a, Account b, BigDecimal amount){
        a.withdraw(amount);
        b.deposit(amount);
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Account a : accounts) {
            total = a.interestEarned().add(total);
        }
        return total;
    }

    public String getStatement() {
        String statement;
        statement = "Statement for " + name + "\n";
        BigDecimal total = BigDecimal.valueOf(0);
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = a.sumTransactions().add(total);
        }
        statement += "\nTotal In All Accounts " + new CurrencyManager().toDollarsAbs(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        BigDecimal total = BigDecimal.valueOf(0);
        for (Transaction t : a.transactions) {
            s += "  " + (t.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit") + " " + new CurrencyManager().toDollarsAbs(t.getAmount())+ "\n";
            total = t.getAmount().add(total);
        }
        s += "Total " + new CurrencyManager().toDollarsAbs(total);
        return s;
    }

}
