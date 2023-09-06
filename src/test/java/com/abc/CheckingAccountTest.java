package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.abc.AccountTypes.CheckingAccount;
import com.abc.MainClasses.Account;
import com.abc.MainClasses.AccountType;

public class CheckingAccountTest {
    @Test
    //Confirm that the account type is CHECKING
    public void checkingAccountType() {
        Account checkingAccount = new CheckingAccount();
        System.out.println(checkingAccount.getClass());
        
        assertTrue("Unexpected account type", checkingAccount.getAccountType() == AccountType.CHECKING);
    }

    @Test
    //Validate the interest earned
    public void interestEarnedFromChecking() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(150);

        assertEquals(0.15, checkingAccount.interestEarned(), 1e-15);
    }
}