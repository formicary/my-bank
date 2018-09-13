package com.abc;

import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author David
 */
public class MaxiSavingsAccount extends Account
{
    private static final double INTEREST_WITHDRAWALS = 0.001;
    public static final double INTEREST_NO_WITHDRAWALS = 0.05;
    public static final int  MILLISECONDS = 86400000;
    
    
    public MaxiSavingsAccount()
    {
        super(Account.MAXI_SAVINGS);
    }
    
    @Override
    public double interestEarned() 
    {
        double amount = sumTransactions();
        Date currentDate = DateProvider.getInstance().now();
        // No transactions (new account) or no money in account
        //I'm assuming having no transactions doesnt count as "no withdrawals in the past 10 days" as the account hasn't existed for that long
        if (transactions.isEmpty() || amount <= 0 ) 
        {
            return 0;
        }
        else 
        {
            for (int i = transactions.size() - 1; i >=0 ; i--)
            {
                if (transactions.get(i).amount < 0)
                {
                    Transaction withdrawal = transactions.get(i);
                    long days = (currentDate.getTime() - withdrawal.getTransactionDate().getTime())/MILLISECONDS;

                    if (days > 10)
                    {
                        return amount * INTEREST_NO_WITHDRAWALS;
                    }
                }
            }
            return amount * INTEREST_WITHDRAWALS;
        }
    }
}
