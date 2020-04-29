package com.abc;

import java.util.List;

public interface Account {

    double calculateInterest();
    String getAccountType();
    double getBalance();
    void setBalance(double amount);
    List<Transaction> getTransactions();

    CommonOperationsHelper getCommonOperation();

    default void deposit(double amount) {
        getCommonOperation().deposit(amount, this);
    }
    default void withdraw(double amount) {
        getCommonOperation().withdraw(amount, this);
    }
    default void transferAmount(double amount, Account otherAccount) {
        getCommonOperation().transferAmount(amount, this, otherAccount);
    }

}
