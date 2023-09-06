package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.AccountTypes.SavingsAccount;
import com.abc.MainClasses.Account;
import com.abc.MainClasses.AccountType;

public class SavingsAccountTest {
     @Test
     //Confirm that the account type is SAVINGS
    public void savingsAccountType() {
        Account savingsAccount = new SavingsAccount();
        System.out.println(savingsAccount.getClass());
        
        assertEquals(AccountType.SAVINGS, savingsAccount.getAccountType());
    }

    @Test
    //Validate the interest earned
    public void interestEarnedForSavings() {
        Account SavingsAccount = new SavingsAccount();
        SavingsAccount.deposit(1500);

        assertEquals(2.0, SavingsAccount.interestEarned(), 1e-15);
    }
}