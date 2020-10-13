package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        accountTypeName = "Checking Account";
    }

    @Override
    public double interestEarned() {
        double amount = 0;
        double interest = 0;
        long daysFromLastInterestEarned = ChronoUnit.DAYS.between(getDateOfLastInterestsEarned(), LocalDate.now());
        while (daysFromLastInterestEarned >= 0) {
            amount = countAllTransactionsForOneDay(LocalDate.now().minusDays(daysFromLastInterestEarned));
            interest += amount * 0.001 / 365;
            daysFromLastInterestEarned--;
        }
        setDateOfLastInterestsEarned(LocalDate.now());
        return interest;

    }
}
