package com.abc;

public class SavingsAccount extends Account{

    public float firstInterestRate = 0.1f;
    public float secondInterestRate = 0.2f;
    public double firstRateLimit = 1000.0;

    SavingsAccount(){
        super(AccountType.SAVINGS);
    }

    // The interest rate has been compounded daily
    // This will break tests in class BankTest
    @Override
    public double interestEarned() {
        double amount = sumTransactions();

        if (amount > 0.0 && amount <= firstRateLimit)
            return amount * (firstInterestRate/GlobalConsts.DAYS_IN_YEAR);
        else if (amount > firstRateLimit)
            return (firstRateLimit*(firstInterestRate/GlobalConsts.DAYS_IN_YEAR))+
                    ((amount-firstRateLimit)*(secondInterestRate/GlobalConsts.DAYS_IN_YEAR));
        else return 0.0;
    }
}
