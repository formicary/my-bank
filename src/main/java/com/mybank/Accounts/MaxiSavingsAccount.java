package com.mybank.Accounts;

import com.mybank.Transaction;
import com.mybank.Utlities.AccountType;
import com.mybank.Utlities.DateProvider;
import com.mybank.Utlities.TransactionType;

public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount() {
        super(AccountType.MAXI_SAVINGS);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().isAfter(DateProvider.getInstance().tenDays())
                    && transaction.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
                return amount * 0.001;
            }
        }
        return amount * 0.05;
    }

}
