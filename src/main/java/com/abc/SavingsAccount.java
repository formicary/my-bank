package com.abc;

class SavingsAccount extends Account {

    SavingsAccount() {
        super(AccountType.SAVINGS);
    }

    @Override
    double calcInterest() {
        double oldInterest = getEarnedInterest();
        long numDays = daysSinceInterestApplied();

        if (numDays >= 1) {
            double interest = (getBalance() <= 1000)
                    ? getBalance() * (0.001 / 365) * numDays
                    : 1 + ((getBalance() - 1000) * (0.002 / 365) * numDays);

            applyInterest(interest);
        }

        return getEarnedInterest() - oldInterest;
    }

    @Override
    String genStatement() {
        return genStatement("Savings Account");
    }
}
