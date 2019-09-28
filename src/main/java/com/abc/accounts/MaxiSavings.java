package com.abc.accounts;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @project MyBank
 */
public class MaxiSavings extends Account {

    protected double secIntRate;
    protected double secAccrueRate;

    public MaxiSavings() {
        super();
        init();
    }

    public MaxiSavings(LocalDateTime date) {
        super(date);
        init();
    }

    private void init() {
        intRate = 0.05;
        accrueRate = intRate / 365;
        secIntRate = 0.001;
        secAccrueRate = secIntRate / 365;
    }

    @Override
    protected void compoundInterest() {

        double earnedInt;

        boolean hadWithdrawal = hadWithdrawalInPast(10);
        earnedInt = (hadWithdrawal) ? (balance * secIntRate) : (balance * intRate);
        balance += earnedInt;
        totalEarnedInt += earnedInt;
    }

    @Override
    protected void accrueInterest() {
        intRate += accrueRate;
        secIntRate += secAccrueRate;
    }

    boolean hadWithdrawalInPast(int numbOfDays) {
        LocalDateTime today = this.getDateOfLastUpdate();
        return transactions.stream()
                .anyMatch(t -> (t.getTransactionType() == 0) && (DAYS.between(t.getTransactionDate(), today) <= numbOfDays));
    }

    @Override
    public String toString() {
        return "Maxi Savings Account";
    }
}
