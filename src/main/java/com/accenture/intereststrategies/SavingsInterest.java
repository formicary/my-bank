package com.accenture.intereststrategies;

import com.accenture.Bank;
import com.accenture.Transaction;
import com.accenture.DollarAmount;

import java.util.List;

public class SavingsInterest implements InterestStrategy {

    private static DollarAmount ONE_THOUSAND_DOLLARS = DollarAmount.fromInt(1000);

    private static double POINT_1_PERCENT = 0.001;
    private static double POINT_2_PERCENT = 0.002;

    public DollarAmount getInterest(List<Transaction> transactions) {

        double pointOnePercentAnualinterestRatePerDay = POINT_1_PERCENT / Bank.DAYS_IN_YEAR;
        double pointTWOPercentAnualinterestRatePerDay = POINT_2_PERCENT / Bank.DAYS_IN_YEAR;

        DollarAmount accountBalance = Transaction.sum(transactions);

        if (accountBalance.lteq(ONE_THOUSAND_DOLLARS)) {
            return checkNotLessThanZero(accountBalance.times(pointOnePercentAnualinterestRatePerDay));
        }
        else {
            DollarAmount firstThousandInterestPayment = ONE_THOUSAND_DOLLARS.times(pointOnePercentAnualinterestRatePerDay);
            DollarAmount aboveFirstThousandInterestPayment = accountBalance.minus(ONE_THOUSAND_DOLLARS).times(pointTWOPercentAnualinterestRatePerDay);
            return checkNotLessThanZero(firstThousandInterestPayment.plus(aboveFirstThousandInterestPayment));
        }
    }

    private DollarAmount checkNotLessThanZero(DollarAmount interestForDay) {
        if (interestForDay.lteq(DollarAmount.fromInt(0)))
            return DollarAmount.fromInt(0);
        else
            return interestForDay;
    }


    @Override
    public String toString() {
        return "Savings Interest Strategy";
    }

}
