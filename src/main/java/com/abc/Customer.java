package com.abc;

import com.abc.accounts.Account;
import com.abc.util.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Math.abs;

public class Customer {

    private final String ID;

    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        ID = UUID.randomUUID().toString();
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getID() {
        return ID;
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

    public Money totalInterestEarned() {
        Money totalInterestPaid = new Money(BigDecimal.ZERO);
        for (Account a : accounts)
            totalInterestPaid = totalInterestPaid.plus(a.interestEarned());
        return totalInterestPaid;
    }

//    public String getStatement() {
//        String statement = null;
//        statement = "Statement for " + name + "\n";
//        double total = 0.0;
//        for (Account a : accounts) {
//            statement += "\n" + statementForAccount(a) + "\n";
//            total += a.sumTransactions();
//        }
//        statement += "\nTotal In All accounts " + toDollars(total);
//        return statement;
//    }

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


    @Override
    public String toString() {
        return name + " (" + format(getNumberOfAccounts(), "account") + ")";
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
