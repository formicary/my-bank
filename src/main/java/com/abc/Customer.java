package com.abc;

import java.util.ArrayList;
import java.util.List;
import static com.abc.Account.*;
import static java.lang.Math.abs;

public class Customer {
    private String AcountHolder;
    private List<Account> ACCs;

    public Customer(String name) {
        this.AcountHolder = name;
        this.ACCs = new ArrayList<Account>();
    }

    public String getName() {
        return AcountHolder;
    }

    public Customer openAccount(Account account) {
        ACCs.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return ACCs.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : ACCs)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + AcountHolder + "\n";
        double total = 0.0;
        for (Account a : ACCs) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.TotalForTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";
       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:s += "Checking Account\n"; break;
            case Account.SAVINGS:s += "Savings Account\n";break;
            case Account.MAXI_SAVINGS:s += "Maxi Savings Account\n";break;
        }
        //Now total up all the transactions
        double total = 0.0;
        
        for (Transaction t : a.transactions) {
            switch (t.getTATtype()){
                case Transaction.DEP:s += " Deposit "
                        + toDollars(t.getAM()) + "\n";break;
                
                case Transaction.TF:
                    Account TargetACC = t.getTargetACC();
                    int accT = TargetACC.getAccountType();
                    s += " transfer " + (t.getAM() >= 0 ? "from " : "to ")+ toString(accT)
                        + " " + toDollars(t.getAM()) + "\n";break;
                
                case Transaction.WD:s += " withdrawal " 
                        + toDollars(t.getAM()) + "\n";break;
            }
           total += t.getAM();
        }
        s += "Total " + toDollars(total);
        return s;
    }
    
    public String toString(int ACCT){
    switch(ACCT){
        
        case MAXI_SAVINGS: return "Maxi Savings Account";
        case SAVINGS:return"Savings Account";
        default:return "Checking Account";
}       
}

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
