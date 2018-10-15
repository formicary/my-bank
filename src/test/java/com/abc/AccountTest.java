package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AccountTest {

    @Test
     public void testDepoWithd(){

        Account account = new Account(Account.CHECKING);
        account.withdraw(50);
        System.out.println(account.sumTransactions());
        assertEquals(account.sumTransactions(), 0.0, 0.0);
        account.deposit(50);
        account.withdraw(25);
        assertEquals(account.sumTransactions(), 25.0, 0.0);


        System.out.println(account.getLastDeposit().amount);
        System.out.println(account.getLastDeposit().getDate());
    }
}
