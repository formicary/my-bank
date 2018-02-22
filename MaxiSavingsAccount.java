package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MaxiSavingsAccount extends Account {

    private static final double INTEREST_PER_ANNUM_BASE = 0.001d;
    private static final double INTEREST_PER_ANNUM_INCREASED = 0.05d;

    public MaxiSavingsAccount() {
        super("Maxi-Savings");
    }

    // calculates the total interest paid on account - depends on type of account
    // returns double value
    @Override
    public double interestPaidOnAccount() {
        long days;
        double interestEarned = 0d, currentBalance, lastBalance;
        int size = getTransactions().size();
        LocalDate lastWithdrawalDate;

        if(!getTransactions().isEmpty()) {
            currentBalance = getTransactions().get(0).getAmount();
            lastWithdrawalDate = getTransactions().get(0).getDate(); // the first possible date of withdrawal
            for (int i = 0; i < size; i++) {
                if (getTransactions().get(i).getAmount() < 0d) {      // if withdraw was performed set new value for lastWithdrawalDate
                    lastWithdrawalDate = getTransactions().get(i).getDate();
                    System.out.println(lastWithdrawalDate);
                }
                days = ChronoUnit.DAYS.between(getTransactions().get(i).getDate(), (i == (size - 1) ? LocalDate.now() : getTransactions().get(i + 1).getDate())); // no. of days between two transactions. in the last iteration no. of days up to now must be calculated
                lastBalance = currentBalance;
                for (int d = 0; d < days; d++) {
                    if (ChronoUnit.DAYS.between(lastWithdrawalDate, getTransactions().get(i).getDate().plusDays(d)) >= 4) {
                        currentBalance += currentBalance * (INTEREST_PER_ANNUM_INCREASED / (getTransactions().get(i).getDate().plusDays(d).isLeapYear() ? 366 : 365));
                    } else {
                        currentBalance += currentBalance * (INTEREST_PER_ANNUM_BASE / (getTransactions().get(i).getDate().plusDays(d).isLeapYear() ? 366 : 365));
                    }
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
        LocalDate lastWithdrawalDate;
        List<Transaction> allTransactions = new ArrayList<>();

        if(!getTransactions().isEmpty()) {
            currentBalance = getTransactions().get(0).getAmount();
            lastWithdrawalDate = getTransactions().get(0).getDate(); // the first possible date of withdrawal
            for (int i = 0; i < size; i++) {
                allTransactions.add(getTransactions().get(i));
                if (getTransactions().get(i).getAmount() < 0) {      // if withdraw was performed set new value for lastWithdrawalDate
                    lastWithdrawalDate = getTransactions().get(i).getDate();
                }
                days = ChronoUnit.DAYS.between(getTransactions().get(i).getDate(), (i == (size - 1) ? LocalDate.now() : getTransactions().get(i + 1).getDate())); // no. of days between two transactions. in the last iteration no. of days up to now must be calculated
                lastBalance = currentBalance;
                for (int d = 0; d < days; d++) {
                    if (ChronoUnit.DAYS.between(lastWithdrawalDate, getTransactions().get(i).getDate().plusDays(d)) >= 4) {
                        newAmount = currentBalance * (INTEREST_PER_ANNUM_INCREASED / (getTransactions().get(i).getDate().plusDays(d).isLeapYear() ? 366 : 365));
                        currentBalance += newAmount;
                    } else {
                        newAmount = currentBalance * (INTEREST_PER_ANNUM_BASE / (getTransactions().get(i).getDate().plusDays(d).isLeapYear() ? 366 : 365));
                        currentBalance += newAmount;
                    }
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