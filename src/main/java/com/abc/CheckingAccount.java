package com.abc;

public class CheckingAccount extends Account{
    private static final double defaultInterestRate = 0.001;
    public CheckingAccount(){
        super(AccountTypeEnum.CHECKING);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        return amount * defaultInterestRate;
    }
}
