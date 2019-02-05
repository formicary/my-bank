package com.abc;

import com.abc.Exceptions.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    
        private static final double DOUBLE_DELTA = 1e-15;


    @Test //Test customer statement generation
    public void depositTest(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        assertEquals(100.0,checkingAccount.sumTransactions(),DOUBLE_DELTA);


                
    }
    @Test(expected = NotEnoughFundsAvailableException.class) 
    public void withdrawTestException() throws NotEnoughFundsAvailableException{

        Account checkingAccount = new Account(AccountType.CHECKING);


        checkingAccount.withdraw(100.0);
        assertEquals(100.0,checkingAccount.sumTransactions(),DOUBLE_DELTA);



    }
    
    @Test
    public void depositAndWithdrawTest() throws NotEnoughFundsAvailableException{

        Account checkingAccount = new Account(AccountType.CHECKING);


        checkingAccount.deposit(100.0);
        assertEquals(100.0,checkingAccount.sumTransactions(),DOUBLE_DELTA);
        checkingAccount.withdraw(50.0);
        assertEquals(50.0,checkingAccount.sumTransactions(),DOUBLE_DELTA);

    }
}
