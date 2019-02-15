package com.abc;

import java.util.ArrayList;
import java.util.Collections;
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
        if (accounts.contains(account))
            throw new UnsupportedOperationException("This account has already been added to the list of users' " +
                    "accounts");
        else {
            accounts.add(account);
            account.linkAccWithCustomer();
        }
    }

    /** This method transfers money from one account to another of a customer.
     *
     * @param accountFrom The account we are transferring from.
     * @param accountTo The account we are transferring to.
     * @param amount The amount to be transferred between accounts.
     */

    public void transferBetweenAccount(Account accountFrom, Account accountTo, double amount) {
        if (accountFrom == accountTo) {
            throw new IllegalArgumentException("The account from which the amount would be taken is the same" +
                    "as the account ");
        }

        else if (!accounts.contains(accountFrom) || !accounts.contains(accountTo)) {
            throw new IllegalArgumentException("Both accounts need to be held by the same customer");
        }

        else if (amount <= 0) {
            throw new IllegalArgumentException("The amount transferred needs to be positive");
        }

        else {
            accountFrom.withdraw(amount);
            accountTo.deposit(amount);
        }

    }


    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";

        statement += makeSymbolLine('‾', 30);

        for (Account account : accounts)
            statement += getStatementForAccount(account);

        statement = statement.replaceAll("\n$", "");
        statement += "\n" + makeSymbolLine('_', 20);

        return statement;
    }

    private String getStatementForAccount(Account a) {
        String statement = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
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

        statement += "\n" + makeSymbolLine('-', 20);

        //Total up all transactions
        if (a.getTransactions().size() > 0)
            for (Transaction t : a.getTransactions())
                statement += "  *" + (t.isWithdrawable()? "withdrawal" : "deposit")
                        + " $" + Account.decimalFormatter.format(t.getTransactionAmount()) + " - " +
                        t.getFormattedTransaction() + "\n";
        else
            statement += "  No transactions made to this date\n";

        statement += makeSymbolLine('-', 20);

        statement += "Total: " + Account.decimalFormatter.format(a.getBalance()) + "\n\n";

        return statement;
    }

    public double getTotalInterestEarned() {
        double total = 0;

        for (Account a:accounts) {
            total+=a.getEarnedInterest();
        }

        return total;
    }

    public static String makeSymbolLine(char symbol, int numChars) {
        return String.join("", Collections.nCopies(numChars, Character.toString(symbol))) + "\n";
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
}
