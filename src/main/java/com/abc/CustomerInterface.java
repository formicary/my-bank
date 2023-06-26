package com.abc;

public interface CustomerInterface {
    String getName();

    Customer openAccount(Account account);

    int getNumberOfAccounts();
    double totalInterestEarned();
    String getStatement();
}
