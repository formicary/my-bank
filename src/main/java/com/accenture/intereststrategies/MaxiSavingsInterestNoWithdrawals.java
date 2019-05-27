package com.accenture.intereststrategies;

import com.accenture.Bank;
import com.accenture.Transaction;
import com.accenture.DollarAmount;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MaxiSavingsInterestNoWithdrawals implements InterestStrategy {

    private final static double POINT_ONE_PERCENT = 0.001;
    private final static double FIVE_PERCENT = 0.05;

    private final int numberOfDaysWithoutWithdrawals;

    public MaxiSavingsInterestNoWithdrawals(int numberOfDaysWithoutWithdrawals) {
        this.numberOfDaysWithoutWithdrawals = numberOfDaysWithoutWithdrawals;
    }

    @Override
    public DollarAmount getInterest(List<Transaction> transactions) {

        double noWithdrawalsInTheLast10DaysDailyInterest = FIVE_PERCENT / Bank.DAYS_IN_YEAR;
        double withdrawalsInLast10DaysDailyInterest = POINT_ONE_PERCENT / Bank.DAYS_IN_YEAR;

        DollarAmount accountBalance = Transaction.sum(transactions);
        DollarAmount interest;

        if (withdrawalsInLast10Days(transactions)) {
            interest = accountBalance.times(withdrawalsInLast10DaysDailyInterest);
        }
        else {
            interest = accountBalance.times(noWithdrawalsInTheLast10DaysDailyInterest);
        }

        return checkNotLessThanZero(interest);
    }

    private boolean withdrawalsInLast10Days(List<Transaction> transactions) {
        Instant now = Instant.now();
        Instant cuttOffPoint = now.minus(numberOfDaysWithoutWithdrawals, ChronoUnit.DAYS);

        return transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.WITHDRAWAL)
                .anyMatch(transaction -> transaction.getTransactionDate().isAfter(cuttOffPoint));
    }


    private DollarAmount checkNotLessThanZero(DollarAmount interestForDay) {
        if (interestForDay.lteq(DollarAmount.fromInt(0)))
            return DollarAmount.fromInt(0);
        else
            return interestForDay;
    }

    @Override
    public String toString() {
        return "MaxiSavingsInterestNoWithdrawals Strategy";
    }

}
