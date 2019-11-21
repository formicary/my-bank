package com.abc;

import com.abc.utils.DateConstants;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private Customer elliot;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;

    private static Date yesterday;
    private static Date lastyear;

    @BeforeClass
    public static void setupDates() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);

        yesterday = new Date();
        yesterday.setTime(DateProvider.getInstance().now().getTime() - (DateConstants.ONE_DAY));

        lastyear = calendar.getTime();
    }

    /**
     * Easier to create a single, and therefore consistent, customer which is reinitialised prior to each test, than to
     * create new customers for each test.
     */
    @Before
    public void defaultCustomer() {
        elliot = new Customer("Elliot Alderson");
    }

    /**
     * Same premise as creating a default customer. Accounts of each type are initialised prior to tests, ensuring
     * consistency across test cases.
     */
    @Before
    public void defaultAccounts() {
        checkingAccount = new CheckingAccount();
        savingsAccount = new SavingsAccount();
        maxiSavingsAccount = new MaxiSavingsAccount();
    }

    /**
     * Tests against the appropriate generation of customer statements.
     */
    @Test
    public void testApp() {
        elliot.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Elliot Alderson\n" +
                "\n" +
                "Checking Account\n" +
                "Total: $100.00\n" +
                "\tDeposit: $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "Total: $3,800.00\n" +
                "\tDeposit: $4,000.00\n" +
                "\tWithdrawal: -$200.00\n" +
                "\n" +
                "Cross-account total: $3,900.00", elliot.genStatement());
    }

    /**
     * Test ensuring that accounts are opened successfully under a customer.
     */
    @Test
    public void testOneAccount() {
        elliot.openAccount(checkingAccount);
        assertEquals(1, elliot.getNumberOfAccounts());
    }

    /**
     * Testing that customers can open two accounts successfully.
     */
    @Test
    public void testTwoAccount() {
        elliot.openAccount(savingsAccount)
                .openAccount(checkingAccount);
        assertEquals(2, elliot.getNumberOfAccounts());
    }

    /**
     * Test ensuring that customers can open three accounts successfully.
     */
    @Test
    public void testThreeAccounts() {
        // Chaining is easier than repeatedly referencing the elliot object
        elliot.openAccount(savingsAccount)
                .openAccount(maxiSavingsAccount)
                .openAccount(checkingAccount);
        assertEquals(3, elliot.getNumberOfAccounts());
    }

    /**
     * Testing a customer's ability to transfer money between accounts.
     */
    @Test
    public void testTransfer() {
        // I've added a getter for the accounts of a customer
        // This would typically be used to retrieve certain accounts
        // However, since I already have the accounts, I'll make transactions prior to
        // their opening with Elliot Alderson
        checkingAccount.deposit(2015);
        maxiSavingsAccount.deposit(71);
        checkingAccount.withdraw(1116);

        elliot.openAccount(checkingAccount)
                .openAccount(maxiSavingsAccount);

        // Checking account inequality would benefit from overriding equals
        elliot.transfer(59, checkingAccount, maxiSavingsAccount);

        assertEquals(840, checkingAccount.getBalance(), 0);
    }

    /**
     * Test to guarantee customer's cannot transfer negative funds between accounts. Negative transfers, theoretically,
     * would just be an inverse transfer. Still, we probably don't want that.
     * <p>
     * Testing for the presence of a thrown exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeTransfer() {
        checkingAccount.deposit(2019);
        maxiSavingsAccount.deposit(22);

        elliot.openAccount(checkingAccount)
                .openAccount(maxiSavingsAccount);

        elliot.transfer(-1886, checkingAccount, maxiSavingsAccount);
    }

    /**
     * Test to guarantee customer's cannot transfer more funds than available from one account to another. Violation of
     * the test could cause a recession in production. :(
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExcessFundsTransfer() {
        checkingAccount.deposit(0.50);
        maxiSavingsAccount.deposit(0.25);

        elliot.openAccount(checkingAccount)
                .openAccount(maxiSavingsAccount);

        elliot.transfer(Double.MAX_VALUE, checkingAccount, maxiSavingsAccount);
    }

    /**
     * Test to ensure that transfers can only occur if the customer has, at least, 2 accounts.
     */
    @Test(expected = IllegalStateException.class)
    public void testSingleAccountTranfer() {
        checkingAccount.deposit(50000);

        elliot.openAccount(checkingAccount);

        elliot.transfer(Double.MAX_VALUE, checkingAccount, checkingAccount);
    }

    /**
     * Test to ensure that a customer cannot execute a transfer between the same account.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSameAccountTransfer() {
        checkingAccount.deposit(100);

        elliot.openAccount(checkingAccount)
                .openAccount(maxiSavingsAccount);

        elliot.transfer(50, checkingAccount, checkingAccount);
    }

    /**
     * Test to ensure customers can only execute transfers from accounts they own.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testOwnedAccount() {
        Customer darlene = new Customer("Darlene Alderson");
        Account darleneChecking = new CheckingAccount();

        darleneChecking.deposit(5000);

        darlene.openAccount(darleneChecking)
                .openAccount(maxiSavingsAccount);

        elliot.openAccount(checkingAccount)
                .openAccount(savingsAccount);

        elliot.transfer(1024, darleneChecking, checkingAccount);
    }

    /**
     * Testing that the correct interest rate is applied on a Maxi-savings account which
     */
    @Test
    public void test10DayInterest() {
        Date tenDaysAgo = new Date();
        tenDaysAgo.setTime(DateProvider.getInstance().now().getTime() - DateConstants.TEN_DAYS);

        maxiSavingsAccount.deposit(1500);
        maxiSavingsAccount.withdraw(500);

        elliot.openAccount(maxiSavingsAccount);

        maxiSavingsAccount.getTransactions().get(0).setTransactionDate(tenDaysAgo);
        maxiSavingsAccount.getTransactions().get(1).setTransactionDate(tenDaysAgo);
        maxiSavingsAccount.setPreviousInterestDate(tenDaysAgo);

        maxiSavingsAccount.calcInterest();
        // Checking that interest has compounded up to 10 days worth
        assertEquals((double) 50 / 36.5, maxiSavingsAccount.getEarnedInterest(), 1e-9);
    }

    /**
     * Test ensuring that account interest compounds daily for checking accounts.
     */
    @Test
    public void testCheckingInterest() {
        double zeroDayInterest, oneDayInterest;

        checkingAccount.deposit(1000);
        zeroDayInterest = checkingAccount.calcInterest();

        elliot.openAccount(checkingAccount);

        checkingAccount.setPreviousInterestDate(yesterday);
        oneDayInterest = checkingAccount.calcInterest();

        assertEquals(0, zeroDayInterest, 1e-9);
        assertEquals((double) 1 / 365, oneDayInterest, 1e-9);
    }

    /**
     * Test ensuring that account interest compounds correctly each day for savings accounts.
     */
    @Test
    public void testSavingsInterest() {
        double zeroDayInterest, oneDayInterest;

        savingsAccount.deposit(1000);
        zeroDayInterest = savingsAccount.calcInterest();

        elliot.openAccount(savingsAccount);

        savingsAccount.setPreviousInterestDate(yesterday);
        oneDayInterest = savingsAccount.calcInterest();

        assertEquals(0, zeroDayInterest, 1e-9);
        assertEquals((double) 1 / 365, oneDayInterest, 1e-9);
    }

    /**
     * Test ensuring that interest compounds daily for maxi-savings accounts.
     */
    @Test
    public void testMaxiSavingsInterest() {
        double zeroDayInterest, oneDayInterest;

        maxiSavingsAccount.deposit(1000);
        zeroDayInterest = maxiSavingsAccount.calcInterest();

        maxiSavingsAccount.setPreviousInterestDate(yesterday);
        oneDayInterest = maxiSavingsAccount.calcInterest();

        assertEquals(0, zeroDayInterest, 1e-9);
        assertEquals((double) 1 / 365, oneDayInterest, 1e-9);
    }

    /**
     * Test to ensure that all interest is appropriately calculated for an entire year.
     */
    @Test
    public void testYearInterest() {
        checkingAccount.deposit(1000);
        savingsAccount.deposit(1000);

        maxiSavingsAccount.deposit(1500);
        maxiSavingsAccount.withdraw(500);

        maxiSavingsAccount.getTransactions().get(0).setTransactionDate(lastyear);
        maxiSavingsAccount.getTransactions().get(1).setTransactionDate(lastyear);

        checkingAccount.setPreviousInterestDate(lastyear);
        savingsAccount.setPreviousInterestDate(lastyear);
        maxiSavingsAccount.setPreviousInterestDate(lastyear);

        double checkingInterest = checkingAccount.calcInterest();
        double savingsInterest = savingsAccount.calcInterest();
        double maxiInterest = maxiSavingsAccount.calcInterest();

        assertEquals(1, checkingInterest, 1e-9);
        assertEquals(1, savingsInterest, 1e-9);
        assertEquals(50, maxiInterest, 1e-9);
    }
}
