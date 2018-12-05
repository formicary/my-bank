package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MaxiSavingsAccount extends Account{

    private final float normalInterestRate = 0.1f;
    private final float specialInterestRate = 5.0f;

    MaxiSavingsAccount(){
        super(AccountType.MAXI_SAVINGS);
    }

    // The interest rate has been compounded daily
    // This will break tests in class BankTest
    @Override
    public double interestEarned() {

        if(transactions.isEmpty()){
            return 0.0;
        }
        // If no withdrawals are made within the last 10 days, the special interest rate is applied.
        // Otherwise the normal interest rate is applied.
        for (Transaction transaction : transactions) {
            final long difference =
                    ChronoUnit.DAYS.between(transaction.getTransactionDate(), LocalDate.now());
            if (difference <= 10 && transaction.isWithdrawal()) {
                return (sumTransactions() * (normalInterestRate / GlobalConsts.DAYS_IN_YEAR));
            }
        }
        return (sumTransactions() * (specialInterestRate / GlobalConsts.DAYS_IN_YEAR));
    }
}