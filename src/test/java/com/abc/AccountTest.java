package com.abc;

import com.abc.types.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Account class
 */
public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    /**
     * Test transferring money between two accounts
     */
    public void testTransferringBetweenAccounts() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        Account savingsAccount = new Account(AccountType.SAVINGS);
        bill.openAccount(savingsAccount);

        checkingAccount.transferTo(savingsAccount, 45.0);

        assertEquals(55.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(45.0, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    }

}
