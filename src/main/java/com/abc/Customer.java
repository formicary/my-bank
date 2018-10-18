package com.abc;

import com.abc.Accounts.Account;
import com.abc.util.Money;

import java.math.BigDecimal;
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
        Money total = Money.ZERO;
        for (Account a : accounts)
            total = total.add(a.interestEarned());
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        Money total = Money.ZERO;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add( a.sumTransactions());
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
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
        Money total = Money.ZERO;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount().compareTo(Money.ZERO) < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total = total.add(t.getAmount());
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(Money d){
        return String.format("$%,.2f", d.abs().doubleValue()); // converts BigDecimal back to decimal for displaying values
    }

    /**
     * transfers funds from one account to another
     * @param amount amount being transferred
     * @param from funds being taken from
     * @param to funds being given to
     */
    public void transfer(Money amount , Account from, Account to){

        // if transferring to the same account
        if (from == to) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        // if one of the accounts is not attached to the customer then throw exception
        if (!existsAccount(from) || !existsAccount(to)) {
            throw new IllegalArgumentException("Both accounts must attached to the customer");
        }

        from.withdraw(amount, Transaction.TRANSFER);// if not enough funds then should throw an exception and not do anything
        to.deposit(amount, Transaction.TRANSFER);

    }

    /**
     * helper function to determine if an account is attached to a customer
     * @param account
     * @return true if account exists and false if it does not
     */
    private boolean existsAccount(Account account){
        return accounts.contains(account);
    }
}
