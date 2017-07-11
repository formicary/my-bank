package com.abc;

import static com.abc.Account.AccountType.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void testOpenAccount1() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount("AccSavings", new Account(SAVINGS));
        oscar.openAccount("AccMaxi", new Account(MAXI_SAVINGS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenAccount2() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount("AccSavings", new Account(SAVINGS));
        oscar.openAccount("AccSavings", new Account(CHECKING));
    }

    @Test
    public void testGetNumberOfAccounts() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount("Acc1", new Account(SAVINGS));

        int expected = 1;
        int result = oscar.numberOfAccounts();
        assertEquals(expected, result);

        oscar.openAccount("Acc2", new Account(CHECKING));

        expected = 2;
        result = oscar.numberOfAccounts();
        assertEquals(expected, result);
    }

    @Test
    public void testGetStatement() {
        Customer oscar = new Customer("Oscar");

        Account checkingAccount = new Account(CHECKING);
        Account savingsAccount = new Account(SAVINGS);

        oscar.openAccount("AccChecking", checkingAccount);
        oscar.openAccount("AccSavings", savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        String expected = "Statement for Oscar\n"
                + "\n"
                + "Savings Account\n"
                + "  deposit $4,000.00\n"
                + "  withdrawal $200.00\n"
                + "Total $3,800.00\n"
                + "\n"
                + "Checking Account\n"
                + "  deposit $100.00\n"
                + "Total $100.00\n"
                + "\n"
                + "Total In All Accounts $3,900.00";
        String result = oscar.createStatement();

        assertEquals(expected, result);
    }

    @Test
    public void testTransferSuccess() {
        Customer oscar = new Customer("Oscar");

        Account savings = new Account(SAVINGS);
        Account checking = new Account(CHECKING);

        oscar.openAccount("Acc1", savings);
        oscar.openAccount("Acc2", checking);

        savings.deposit(1000);

        oscar.transfer("Acc1", "Acc2", 750.0);

        double expectedSavings = 250.0;
        double expectedChecking = 750.0;

        double resultSavings = savings.sumTransactions();
        double resultChecking = checking.sumTransactions();

        assertEquals(expectedSavings, resultSavings, 0.001);
        assertEquals(expectedChecking, resultChecking, 0.001);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferInvalidAccount() {
        Customer oscar = new Customer("Oscar");

        Account savings = new Account(SAVINGS);
        Account checking = new Account(CHECKING);

        oscar.openAccount("Acc1", savings);
        oscar.openAccount("Acc2", checking);

        oscar.transfer("Acc1", "Acc3", 500.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferInsufficientFunds() {
        Customer oscar = new Customer("Oscar");

        Account savings = new Account(SAVINGS);
        Account checking = new Account(CHECKING);

        oscar.openAccount("Acc1", savings);
        oscar.openAccount("Acc2", checking);

        savings.deposit(200.0);

        oscar.transfer("Acc1", "Acc2", 750.0);

        double expectedSavings = 250.0;
        double expectedChecking = 750.0;

        double resultSavings = savings.sumTransactions();
        double resultChecking = checking.sumTransactions();

        assertEquals(expectedSavings, resultSavings, 0.001);
        assertEquals(expectedChecking, resultChecking, 0.001);
    }
}
