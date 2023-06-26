package com.abc;

public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount(){
        super(AccountTypeEnum.MAXI_SAVINGS);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 1000)
            return amount * 0.02;
        if (amount <= 2000)
            return 20 + (amount - 1000) * 0.05;
        return 70 + (amount - 2000) * 0.1;
    }
}
