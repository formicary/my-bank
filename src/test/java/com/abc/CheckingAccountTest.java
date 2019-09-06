package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.*;

public class CheckingAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testFlatRate() throws Exception {

        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -10);

        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(100.0);

        provider.reset();

        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;

        assertEquals(1.0, interest, DOUBLE_DELTA);
    }

    @Test
    public void testCheckingMultipleTransactions() throws Exception {

        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -20);

        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        // i1 = (100 * 1.001 ^ 10 )
        checkingAccount.deposit(400.0);
        checkingAccount.withdraw(300.0);

        // i2 = ((1000+i1) * 1.001 ^ 10 )
        provider.add(Calendar.DAY_OF_MONTH, 10);
        checkingAccount.deposit(1000);

        provider.reset();

        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;

        assertEquals(12.06, interest, DOUBLE_DELTA);
    }

}