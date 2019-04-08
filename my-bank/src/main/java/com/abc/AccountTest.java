package com.abc;

import org.junit.Test;
import static org.junit.Assert.*;



public class AccountTest {
    @Test
    public void account() {
        Account temp_acc = new Account(0);
        assertTrue(temp_acc instanceof Account);
        assertEquals(temp_acc.getAccountType(),2);
        assertEquals(temp_acc.getTransactions().size(),0);

        temp_acc.deposit(10000);

        assertEquals(temp_acc.getTransactions().size(),1);
        assertEquals(temp_acc.currentBalance(),10000.0);
        assertEquals(temp_acc.interestEarned(),500.0);
        
        temp_acc.withdraw(2000);

        assertEquals(temp_acc.getTransactions().size(),2);
        assertEquals(temp_acc.interestEarned(),8.0);
        assertTrue(temp_acc.recentWithdrawals());

    }
}