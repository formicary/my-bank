package com.abc;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount() {
        super();
    }

    @Override
    double calculateInterest(double amount, LocalDate dateInQuestion) {
        //Filter transactions to get withdrawals
        List<Transaction> withdrawals = transactions.stream()
                .filter(
                        t -> (t.getAmount() < 0) && (t.getTransactionDate().isBefore(dateInQuestion) ||
                                t.getTransactionDate().equals(dateInQuestion)))
                .collect(Collectors.toList());

        //If no withdrawals then 5% interest
        if (withdrawals.size() == 0) {
            return (amount * 0.05)/365;
        }

        //Get period between date in question and last withdrawal
        int period = Period.between(
                withdrawals.get(withdrawals.size() - 1).getTransactionDate(), dateInQuestion
        ).getDays();

        //If there has been a withdrawal within the last 10 days, interest is 0.1%
        if (period < 10) {
            return (amount * 0.001) / 365;
        }

        //Interest is 5% otherwise
        return (amount * 0.05) / 365;
    }

    @Override
    String getAccountString() {
        return "Maxi Savings Account\n";
    }
}
