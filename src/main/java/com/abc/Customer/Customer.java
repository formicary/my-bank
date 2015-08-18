package main.java.com.abc.Customer;

import java.util.ArrayList;
import java.util.List;

import main.java.com.abc.Util;
import main.java.com.abc.Accounts.AccountBase;
import main.java.com.abc.Transactions.TransRecord;

public class Customer {
    private String name;
    private List<AccountBase> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<AccountBase>();
    }

    public String GetName() {
        return name;
    }

    public Customer OpenAccount(AccountBase account) {
        accounts.add(account);
        return this;
    }
    
    public void CloseAccount(AccountBase account){
    	
    	account.Close();
    	accounts.remove(account);
    }

    public int GetNumberOfAccounts() {
        return accounts.size();
    }

    public double GetTotalInterestEarned() {
        double total = 0;
        for (AccountBase a : accounts)
            total += a.GetInterestEarned();
        return total;
    }

    public String GetAccountsStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (AccountBase acc : accounts) {
            statement += "\n" + this.GetStatementForAccount(acc) + "\n";
            total += acc.GetBalanceSafe();
        }
        statement += "\nTotal In All Accounts " +  Util.toDollars(total);
        return statement;
    }

    private String GetStatementForAccount(AccountBase acc) {
        String statement = "";

        statement += acc.GetAccountType() + "\n";
             
        //Now total up all the transactions
        for (TransRecord record : acc.GetRecords()) {
            statement += "  " + record.GetSummary() + "\n";
        }
        statement += "Total " + Util.toDollars(acc.GetBalanceSafe());
        return statement;
    }

}
