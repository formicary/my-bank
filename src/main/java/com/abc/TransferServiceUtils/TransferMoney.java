package com.abc.TransferServiceUtils;

import com.abc.Account;

public class TransferMoney {

    /*
     * moveMoneyBetweenAccounts takes two accounts and a transfer amount as
     * arguments
     * performs a check if the balance in the source account is higher
     * then updates the balance for both the accounts
     * if lower balance is detected, throws an error
     */

    public void moveMoneyBetweenAccounts(Account source, Account destination, double amount) {
        double sourceBalance = source.getAccountBalance();
        if (sourceBalance >= amount) {
            source.withdraw(amount);
            destination.deposit(amount);
        } else {
            throw new IllegalArgumentException("Balance must be higher than transfer amount");
        }
    }
}
