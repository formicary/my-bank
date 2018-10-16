package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;


public class AccountTest {

     @Test
     public void testSum() {
         Account account = new Account(Account.CHECKING);

         account.deposit(BigDecimal.valueOf(50));
         account.deposit(BigDecimal.valueOf(100));
         account.withdraw(BigDecimal.valueOf(25));

         assertEquals(account.sumTransactions(), BigDecimal.valueOf(125).setScale(2, RoundingMode.HALF_UP));
     }

    @Test
    public void testTransactionNo() {
        Account account = new Account(Account.CHECKING);

        account.deposit(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(100));
        account.withdraw(BigDecimal.valueOf(25));

        assertEquals(account.getNumberOfTransactions(), 3);

    }

    @Test
    public void testLastWithdrawl(){
        Account account = new Account(Account.CHECKING);

        account.deposit(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(100));
        account.withdraw(BigDecimal.valueOf(25));
        assertEquals(account.getLastWithdrawal().getAmount(), BigDecimal.valueOf(-25).setScale(2, RoundingMode.HALF_UP) );
    }

}
