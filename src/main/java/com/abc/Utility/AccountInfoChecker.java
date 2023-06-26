package com.abc.Utility;

import com.abc.Account;

public class AccountInfoChecker {
    public static void isValidAmount(double amount)
    {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

    public static void isSufficientBalance(double amount,Account account)
    {
        if(account.getBalance()<amount)
        {
            throw new IllegalStateException("Amount is not sufficient for this transaction.");

        }
    }
}
