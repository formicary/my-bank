package com.abc.Accounts;

import com.abc.Transaction;
import com.abc.util.Money;

import java.util.List;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        super(Account.CHECKING);
    }

    public Money dailyInterestEarned() {
        Money interestRate = new Money("0.001");
        return dailyInterestAtRate(sumTransactions(), interestRate);
    }
}
