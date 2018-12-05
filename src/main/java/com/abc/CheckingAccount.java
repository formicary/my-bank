package com.abc;

public class CheckingAccount extends Account{

    public float interestRate = 0.1f;

    CheckingAccount(){
        super(AccountType.CHECKING);
    }

    // The interest rate has been compounded daily
    // This will break tests in class BankTest
    @Override
    public double interestEarned() {
        return (sumTransactions())*(interestRate/GlobalConsts.DAYS_IN_YEAR);
    }
}
