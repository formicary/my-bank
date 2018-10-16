package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


public class AccountTest {

    @Test
     public void testDepoWithd(){

        Account account = new Account(Account.CHECKING);
        account.withdraw(BigDecimal.valueOf(50.00));
        System.out.println(account.sumTransactions());
        assertEquals(account.sumTransactions(), BigDecimal.valueOf(0.0));
        account.deposit(BigDecimal.valueOf(50.00));
        account.withdraw(BigDecimal.valueOf(25.00));
        assertEquals(account.sumTransactions(), BigDecimal.valueOf(25.0));


        System.out.println(account.getLastWithdrawal().amount);
        System.out.println(account.getLastWithdrawal().getDate());
    }
}
