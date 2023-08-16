package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCheckingAccountInterestEarned() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, checkingAccount.interestEarnedChecking(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccountInterestEarned() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, savingsAccount.interestEarnedSavings(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSaverAccountHighInterestEarned() {
        Bank bank = new Bank();
        Account maxiSaverAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSaverAccount));
        for (int i = 0; i < 5; i++) {
            maxiSaverAccount.deposit(3000.0);
        }
        ;

        assertEquals(750.0, maxiSaverAccount.interestEarnedMaxiSavings(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSaverAccountLowInterestEarned() {
        Bank bank = new Bank();
        Account maxiSaverAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSaverAccount));
        for (int i = 0; i < 5; i++) {
            maxiSaverAccount.deposit(3000.0);
        }
        ;
        maxiSaverAccount.withdraw(1000.0);

        assertEquals(14.0, maxiSaverAccount.interestEarnedMaxiSavings(), DOUBLE_DELTA);
    }

    @Test
    public void testAccountBalance() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(500);
        checkingAccount.deposit(700);
        checkingAccount.withdraw(200);

        assertEquals(1000, checkingAccount.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testAccountType() {
        Bank bank = new Bank();
        Account maxiSaverAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSaverAccount));

        assertEquals(2, maxiSaverAccount.getAccountType(), DOUBLE_DELTA);
    }
}
