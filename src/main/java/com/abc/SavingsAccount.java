package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SavingsAccount extends Account {

    public SavingsAccount() {
        accountTypeName = "Savings Account";
    }

    @Override
    public double interestEarned() {
        double amount = 0;
        double interest = 0;
        long daysFromLastInterestEarned = ChronoUnit.DAYS.between(getDateOfLastInterestsEarned(), LocalDate.now());
        while (daysFromLastInterestEarned >= 0) {
            amount = countAllTransactionsForOneDay(LocalDate.now().minusDays(daysFromLastInterestEarned));
            if (amount <= 1000)
                interest += amount * 0.001;
            else
                interest += 1 + (amount - 1000) * 0.002;
            daysFromLastInterestEarned--;
        }
        setDateOfLastInterestsEarned(LocalDate.now());
        return interest;

    }
}
