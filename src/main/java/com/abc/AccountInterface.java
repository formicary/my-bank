package com.abc;

public interface AccountInterface {

    void transact(double amount);

    double interestEarned();

    double sumTransactions();

    AccountTypeEnum getAccountType();
}
