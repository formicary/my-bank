package com.accenture.intereststrategies;

import com.accenture.Bank;
import com.accenture.Transaction;
import com.accenture.DollarAmount;

import java.util.List;

public class FlatRateInterest implements InterestStrategy {

    private double interestRate;

    public static FlatRateInterest newInstance(double interestRate) {
        return new FlatRateInterest(interestRate);
    }

    private FlatRateInterest(double interestRate) {
        this.interestRate = interestRate;
    }

    public DollarAmount getInterest(List<Transaction> transactions) {
        double interestRatePerDay = interestRate / Bank.DAYS_IN_YEAR;
        DollarAmount interestForDay = Transaction.sum(transactions).times(interestRatePerDay);

        return checkNotLessThanZero(interestForDay);
    }

    private DollarAmount checkNotLessThanZero(DollarAmount interestForDay) {
        if (interestForDay.lteq(DollarAmount.fromInt(0)))
            return DollarAmount.fromInt(0);
        else
            return interestForDay;
    }

    @Override
    public String toString() {
        return "FlatRateInterest Strategy: " + interestRate;
    }

}
