package com.abc;

import com.abc.ENUMS.TransactionType;

import java.util.ArrayList;
import java.util.List;

import static com.abc.Utility.AccountInfoChecker.isValidAmount;
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
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = "";
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
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
//            s += "  " + (t.getTransactionType()== TransactionType.WITHDRAWL ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            s += "  " + (t.getTransactionType().toString()) + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public boolean transferMoneyBetweenOwnAccount(Account fromAccount, Account toAccount,double amount)  {
        isValidAmount(amount);
        checkIfCustomerOwnsAccounts(fromAccount,toAccount);
        fromAccount.withdraw(amount,TransactionType.FUND_TRANSFER_OWN_ACCOUNT);
        toAccount.deposit(amount,TransactionType.DEPOSIT);
        return true;
    }

    private void checkIfCustomerOwnsAccounts(Account fromAccount, Account toAccount)
    {
        if(!accounts.contains(fromAccount) || !accounts.contains(toAccount))
        {
            throw new IllegalStateException("Customers can transfer between their own accounts only.");
        }
    }

}
