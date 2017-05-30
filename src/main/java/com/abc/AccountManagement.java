package com.abc;

import java.util.Map;

public interface AccountManagement {

    void openAccount(Account account);

    int getNumberOfAccounts();

    double totalInterestEarned();

    String getStatement();

    String statementForAccount(Account a);

    Map<Integer, Account> getAccounts();

    String toDollars(double d);

}
