package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account {

    private static final double INTEREST_PER_ANNUM_BELOW_1000 = 0.001d;
    private static final double INTEREST_PER_ANNUM_ABOVE_1000 = 0.002d;

    public SavingsAccount() {
        super("Savings");
    }

    // calculates the total interest paid on account - depends on type of account
    // returns double value
    @Override
    public double interestPaidOnAccount() {
        long days;
        double interestEarned = 0d, currentBalance, lastBalance;
        int size = getTransactions().size();

        if(!getTransactions().isEmpty()) {
            currentBalance = getTransactions().get(0).getAmount();
            for (int i = 0; i < size; i++) {
                days = ChronoUnit.DAYS.between(getTransactions().get(i).getDate(), (i == (size - 1) ? LocalDate.now() : getTransactions().get(i + 1).getDate())); // no. of days between two transactions. in the last iteration no. of days up to now must be calculated
                lastBalance = currentBalance;
                for (int d = 0; d < days; d++) {
                    currentBalance += currentBalance * ((currentBalance > 1000d ? INTEREST_PER_ANNUM_ABOVE_1000 : INTEREST_PER_ANNUM_BELOW_1000) / (getTransactions().get(i).getDate().plusDays(d).isLeapYear() ? 366 : 365));  //check if the day we are calculating the earnings falls to a leap year or not
                }
                interestEarned += currentBalance - lastBalance;
                currentBalance += (i == (size - 1) ? 0d : getTransactions().get(i + 1).getAmount());
            }
        }
        return interestEarned;
    }

    // creates a list of all transactions including interest earnings deposited at the end of each day
    // returns a List of transactions as instances of Transaction
    @Override
    List<Transaction> buildTransactions() {
        LocalDate newDate;
        double newAmount;
        long days;
        double interestEarned = 0d, currentBalance, lastBalance;
        int size = getTransactions().size();
        List<Transaction> allTransactions = new ArrayList<>();

        if(!getTransactions().isEmpty()) {
            currentBalance = getTransactions().get(0).getAmount();
            for (int i = 0; i < size; i++) {
                allTransactions.add(getTransactions().get(i));

                days = ChronoUnit.DAYS.between(getTransactions().get(i).getDate(), (i == (size - 1) ? LocalDate.now() : getTransactions().get(i + 1).getDate())); // no. of days between two transactions. in the last iteration no. of days up to now must be calculated
                lastBalance = currentBalance;
                for (int d = 0; d < days; d++) {
                    newAmount = currentBalance * ((currentBalance > 1000d ? INTEREST_PER_ANNUM_ABOVE_1000 : INTEREST_PER_ANNUM_BELOW_1000) / (getTransactions().get(i).getDate().plusDays(d).isLeapYear() ? 366 : 365));  //check if the day we are calculating the earnings falls to a leap year or not
                    currentBalance += newAmount;
                    newDate = getTransactions().get(i).getDate().plusDays(d + 1); // the interest earned is deposited on the next day, that's why d+1
                    allTransactions.add(new Transaction(newAmount, newDate));
                }
                interestEarned += currentBalance - lastBalance;
                currentBalance += (i == (size - 1) ? 0d : getTransactions().get(i + 1).getAmount());
            }
        }
        return allTransactions;
    }
}