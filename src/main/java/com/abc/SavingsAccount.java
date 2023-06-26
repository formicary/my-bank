package com.abc;

public class SavingsAccount extends Account{
    private static final double defaultInterestRate = 0.001;
    private static final double secondTierInterestRate = 0.002;
    public SavingsAccount () {
        super(AccountTypeEnum.SAVINGS);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 1000)
            return amount * defaultInterestRate;
        else
            return 1 + (amount - 1000) * secondTierInterestRate;
    }
}
