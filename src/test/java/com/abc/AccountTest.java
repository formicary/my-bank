package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    @Test
    public void checkSumAfterDeposit() {
        Account acc = new Account(AccountType.SAVINGS);
        
        acc.deposit(100);

        assertTrue(100 == acc.sumTransactions());
    }

    @Test
    public void checkSumAfterWithdrawal() {
        Account acc = new Account(AccountType.SAVINGS);
        
        acc.withdraw(100);

        assertTrue(-100 == acc.sumTransactions());

    }

    @Test
    public void checkSumAfterMultipleTransactions() {
        Account acc = new Account(AccountType.SAVINGS);
        
        acc.deposit(100);
        acc.withdraw(10);
        acc.deposit(300);

        assertTrue(390 == acc.sumTransactions());

    }

}
