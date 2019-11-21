package com.abc;

class CheckingAccount extends Account {
    private final double INTEREST_RATE = 0.001 / 365;

    CheckingAccount() {
        super(AccountType.CHECKING);
    }

    @Override
    double calcInterest() {
        double oldInterest = getEarnedInterest();
        long numDays = daysSinceInterestApplied();

        if (numDays >= 1) {
            double cumulativeInterest = INTEREST_RATE * numDays;
            applyInterest(getBalance() * cumulativeInterest);
        }

        return getEarnedInterest() - oldInterest;
    }

    @Override
    String genStatement() {
        return genStatement("Checking Account");
    }
}
