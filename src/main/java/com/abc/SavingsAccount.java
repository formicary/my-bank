package com.abc;

/**
 *
 * @author David
 */
public class SavingsAccount extends Account
{
    public static final double INTEREST_INITIAL = 0.001;
    public static final double INTEREST_AFTER = 0.002;
    public static final double INTEREST_CRITERIA = 1000;
    
    public SavingsAccount()
    {
        super(Account.SAVINGS);
    }

    @Override
    public double interestEarned() 
    {
        double amount = sumTransactions();
        if (amount <= 1000)
            return amount * 0.001;
        else
            return 1 + (amount-1000) * 0.002;
    }
}


