package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() {

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingAccount();

        Customer henry = new Customer("Henry", checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(Transaction.ONE_HUNDRED);
        savingsAccount.deposit(new BigDecimal("4000.00"));
        savingsAccount.withdraw(new BigDecimal("200"));

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit £100.00\n" +
                "Total £100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit £4,000.00\n" +
                "  withdrawal £200.00\n" +
                "Total £3,800.00\n" +
                "\n" +
                "Total In All Accounts £3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar", new SavingAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar", new SavingAccount())
                .openAccount(new SavingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccount() {
        Customer oscar = new Customer("Oscar", new SavingAccount())
                .openAccount(new CheckingAccount())
                .openAccount(new CheckingAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
