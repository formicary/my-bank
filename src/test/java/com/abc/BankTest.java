package com.abc;

import com.abc.accounts.Checking;
import com.abc.accounts.MaxiSavings;
import com.abc.accounts.Savings;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Bank testBank;
    private Customer alice;
    private Customer bob;

    private Checking aliceChecking;
    private Savings aliceSavings;
    private Checking bobChecking;
    private MaxiSavings bobMaxiSavings;

    @Before
    public void init() {
        testBank = new Bank();
        alice = new Customer("Alice");
        bob = new Customer("Bob");

        aliceChecking = new Checking();
        aliceSavings = new Savings();
        bobChecking = new Checking();
        bobMaxiSavings = new MaxiSavings();

        alice.openAccount(aliceChecking).openAccount(aliceSavings);
        bob.openAccount(bobChecking).openAccount(bobMaxiSavings);
        testBank.addCustomer(alice).addCustomer(bob);
    }

    @Test // Test customers are added
    public void testAddCustomer() {
        assertEquals(2, testBank.getCustomers().size());
    }

    @Test // Test customer summary
    public void testCustomerSummary() {
        String expected = "Customer Summary\n" +
                " - Alice (2 accounts)\n" +
                " - Bob (2 accounts)";
        String actual = testBank.customerSummary();

        assertEquals(expected, actual);
    }

    @Test // Test simple interest calculations
    public void testSimpleInterestPaid() {
        Date now = Calendar.getInstance().getTime();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date initDate = new Date(now.getTime() - 365*DAY_IN_MS);

        Transaction init = new Transaction(1000, initDate);

        aliceChecking.addTransaction(init);
        bobChecking.addTransaction(init);
        double expected = 2;
        double actual = testBank.totalInterestPaid();
        assertEquals(expected, actual, DOUBLE_DELTA);
    }

    @Test // Test complex interest calculations
    public void testComplexInterestPaid() {
        Date now = Calendar.getInstance().getTime();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date initDate = new Date(now.getTime() - 365*DAY_IN_MS);
        Date d1 = new Date(now.getTime() - 335*DAY_IN_MS);
        Date d2 = new Date(now.getTime() - 35*DAY_IN_MS);

        Transaction init = new Transaction(1000, initDate);
        Transaction t1 = new Transaction(500, d1);
        Transaction t2 = new Transaction(-250, d2);

        aliceChecking.addTransaction(init)
                .addTransaction(t1)
                .addTransaction(t2);
        aliceSavings.addTransaction(init)
                .addTransaction(t1)
                .addTransaction(t2);
        bobChecking.addTransaction(init)
                .addTransaction(t1)
                .addTransaction(t2);
        bobMaxiSavings.addTransaction(init)
                .addTransaction(t1)
                .addTransaction(t2);

        double expected = (2*1.4351251706737753) + 1.870329168339687 + 69.06268926318388;
        double actual = testBank.totalInterestPaid();
        assertEquals(expected, actual, DOUBLE_DELTA);
    }

}
