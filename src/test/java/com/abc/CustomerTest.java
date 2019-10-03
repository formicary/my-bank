package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.Checking;
import com.abc.accounts.MaxiSavings;
import com.abc.accounts.Savings;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private Customer testCustomer;
    private Checking testChecking;
    private Savings testSavings;
    private MaxiSavings testMaxiSavings;
    private final double DOUBLE_DELTA = 1e-15;

    @Before
    public void init() {
        testCustomer = new Customer("test");
        testChecking = new Checking();
        testSavings = new Savings();
        testMaxiSavings = new MaxiSavings();
    }

    @Test // Test customer name
    public void testName() {
        assertEquals("test", testCustomer.getName());
    }

    @Test // Test opening one account
    public void testOpenOneAccount() {
        testCustomer.openAccount(testChecking);
        assertEquals(testChecking, testCustomer.getAccounts().get(0));
    }

    @Test // Test opening multiple accounts
    public void testOpenMultipleAccounts() {
        testCustomer.openAccount(testChecking)
                .openAccount(testSavings)
                .openAccount(testMaxiSavings);
        assertEquals(3, testCustomer.getAccounts().size());
    }

    @Test // Test customer statement generation
    public void testCustomerStatement(){
        testCustomer.openAccount(testChecking)
                .openAccount(testSavings)
                .openAccount(testMaxiSavings);

        testChecking.deposit(100.0);
        testSavings.deposit(4000.0);
        testMaxiSavings.deposit(1500);
        testMaxiSavings.withdraw(200.0);

        String expected = "Statement for test\n" +
                "\n" +
                "Checking Account\n" +
                "  Deposit: $100.00\n" +
                "Total: $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  Deposit: $4,000.00\n" +
                "Total: $4,000.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  Deposit: $1,500.00\n" +
                "  Withdraw: $200.00\n" +
                "Total: $1,300.00\n" +
                "\n" +
                "Total In All Accounts $5,400.00";
        String actual = testCustomer.getStatement();
        assertEquals(expected, actual);
    }

    @Test // Test earning interest on accounts
    public void testInterestEarned() {
        testCustomer.openAccount(testChecking).openAccount(testSavings);

        Date now = Calendar.getInstance().getTime();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date initDate = new Date(now.getTime() - 365*DAY_IN_MS);
        Transaction t1 = new Transaction(1000, initDate);
        Transaction t2 = new Transaction(2000, initDate);

        testChecking.addTransaction(t1);
        testSavings.addTransaction(t2);

        double expectedInterest = 4;
        double actualInterest = testCustomer.totalInterestEarned();
        assertEquals(expectedInterest, actualInterest, DOUBLE_DELTA);
    }

    @Test // Test transfer
    public void testTransfer() {
        testCustomer.openAccount(testChecking).openAccount(testSavings);
        testChecking.deposit(500);

        testCustomer.transfer(45, testChecking, testSavings);
        assertEquals(45, testSavings.getBalance(), DOUBLE_DELTA);
    }
}
