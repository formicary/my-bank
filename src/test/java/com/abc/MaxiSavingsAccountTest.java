package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.AccountTypes.MaxiSavingsAccount;
import com.abc.MainClasses.Account;
import com.abc.MainClasses.AccountType;

public class MaxiSavingsAccountTest {
    //Confirm that the account type is MAXI_SAVINGS
    public void MaxiSavingsAccountType() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        System.out.println(maxiSavingsAccount.getClass());
        
        assertEquals(AccountType.MAXI_SAVINGS, maxiSavingsAccount.getAccountType());
    }

    @Test
    //Validate the interest earned
    public void interestEarnedForMaxiSavings() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(500);

        assertEquals(10.0, maxiSavingsAccount.interestEarned(), 1e-15);
    }
}