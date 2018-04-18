package com.abc;

import com.abc.Accounts.AccountType;
import com.abc.Utils.BankUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for Bank class which in turn verifies the functionality
 * of the entire "bank" system.
 */
public class BankTests {
    /**
     * The dummy customer name.
     */
    private final String dummyCustomerName = "dummy-customer-name";

    /**
     * The dummy other customer name.
     */
    private final String dummyOtherCustomerName = "dummy-other-customer-name";

    /**
     * The dummy another customer name.
     */
    private final String dummyAnotherCustomerName = "dummy-another-customer-name";

    /**
     * The dummy amount of money.
     */
    private final double dummyAmount = 10000.0;

    /**
     * The dummy other amount of money.
     */
    private final double dummyOtherAmount = 5000.0;

    /**
     * The bank
     */
    private Bank bank;

    /**
     * Sets up before each test.
     */
    @Before
    public void setUp() {
        this.bank = new Bank();
    }

    /**
     * Tests that registering the customer registers the customer with the bank.
     */
    @Test
    public void registeringCustomerRegistersCustomerWithBank() {
        this.bank.registerCustomer(this.dummyCustomerName);

        String expectedCustomerRecord = "dummy-customer-name (0 accounts)";
        String customerSummary = this.bank.getCustomerSummary();

        Assert.assertTrue(customerSummary.contains(expectedCustomerRecord));
    }

    /**
     * Tests that registering multiple customers registers all the customer with the bank
     */
    @Test
    public void registeringMultipleCustomersRegistersCustomersWithBank() {
        this.bank.registerCustomer(this.dummyCustomerName);
        this.bank.registerCustomer(this.dummyOtherCustomerName);
        this.bank.registerCustomer(this.dummyAnotherCustomerName);

        String expectedCustomerRecord = this.dummyCustomerName + " (0 accounts)";
        String expectedOtherCustomerRecord = this.dummyOtherCustomerName + " (0 accounts)";
        String expectedAnotherCustomerRecord = this.dummyAnotherCustomerName + " (0 accounts)";
        String customerSummary = this.bank.getCustomerSummary();

        Assert.assertTrue(customerSummary.contains(expectedCustomerRecord));
        Assert.assertTrue(customerSummary.contains(expectedOtherCustomerRecord));
        Assert.assertTrue(customerSummary.contains(expectedAnotherCustomerRecord));
    }

    /**
     * Tests that opening an account in a customer's name opens the account.
     */
    @Test
    public void openingAccountOpensAccount() {
        int customerId = this.bank.registerCustomer(this.dummyCustomerName);
        this.bank.openAccount(customerId, AccountType.CHECKING);

        String expectedAccountRecord = "Checking Account\nTotal $0.00";
        String customerStatement = this.bank.getCustomerStatement(customerId);

        Assert.assertTrue(customerStatement.contains(expectedAccountRecord));

        String expectedCustomerRecord = this.dummyCustomerName + " (1 account)";
        String customerSummary = this.bank.getCustomerSummary();

        Assert.assertTrue(customerSummary.contains(expectedCustomerRecord));
    }

    /**
     * Tests that opening multiple accounts in a customer's name opens all the accounts.
     */
    @Test
    public void openingMultipleAccountsOpensAccounts() {
        int customerId = this.bank.registerCustomer(this.dummyCustomerName);
        this.bank.openAccount(customerId, AccountType.CHECKING);
        this.bank.openAccount(customerId, AccountType.SAVINGS);
        this.bank.openAccount(customerId, AccountType.MAXI_SAVINGS);

        String expectedAccountRecord = "Checking Account\nTotal $0.00";
        String expectedOtherAccountRecord = "Savings Account\nTotal $0.00";
        String expectedAnotherAccountRecord = "Maxi-Savings Account\nTotal $0.00";
        String customerStatement = this.bank.getCustomerStatement(customerId);

        Assert.assertTrue(customerStatement.contains(expectedAccountRecord));
        Assert.assertTrue(customerStatement.contains(expectedOtherAccountRecord));
        Assert.assertTrue(customerStatement.contains(expectedAnotherAccountRecord));

        String expectedCustomerRecord = this.dummyCustomerName + " (3 accounts)";
        String customerSummary = this.bank.getCustomerSummary();

        Assert.assertTrue(customerSummary.contains(expectedCustomerRecord));
    }

    /**
     * Tests that depositing into account deposits into account.
     */
    @Test
    public void depositingIntoAccountDepositsIntoAccount() {
        int customerId = this.bank.registerCustomer(this.dummyCustomerName);
        int accountId = this.bank.openAccount(customerId, AccountType.CHECKING);

        this.bank.deposit(customerId, accountId, this.dummyAmount);

        String expectedStatement = "Statement for " + this.dummyCustomerName + "\n\n" +
                "Checking Account\n" +
                "  deposit " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "Total " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "\n" +
                "Total In All Accounts " + BankUtils.toDollars(this.dummyAmount);
        String customerStatement = this.bank.getCustomerStatement(customerId);

        Assert.assertEquals(customerStatement, expectedStatement);
    }

    /**
     * Tests that withdrawing from account withdraws from account.
     */
    @Test
    public void withdrawingFromAccountWithdrawsFromAccount() {
        int customerId = this.bank.registerCustomer(this.dummyCustomerName);
        int accountId = this.bank.openAccount(customerId, AccountType.CHECKING);

        this.bank.deposit(customerId, accountId, this.dummyAmount);
        this.bank.withdraw(customerId, accountId, this.dummyAmount);

        String expectedStatement = "Statement for " + this.dummyCustomerName + "\n\n" +
                "Checking Account\n" +
                "  deposit " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "  withdrawal " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "Total " + BankUtils.toDollars(0) + "\n" +
                "\n" +
                "Total In All Accounts " + BankUtils.toDollars(0);
        String customerStatement = this.bank.getCustomerStatement(customerId);

        Assert.assertEquals(customerStatement, expectedStatement);
    }

    /**
     * Tests that multiple deposits and withdrawals made are correctly
     * reflected in the account statement.
     */
    @Test
    public void multipleDepositsAndWithdrawalsReflectedInAccount() {
        int customerId = this.bank.registerCustomer(this.dummyCustomerName);
        int accountId = this.bank.openAccount(customerId, AccountType.CHECKING);

        this.bank.deposit(customerId, accountId, this.dummyAmount);
        this.bank.withdraw(customerId, accountId, this.dummyAmount);
        this.bank.deposit(customerId, accountId, this.dummyAmount);
        this.bank.withdraw(customerId, accountId, this.dummyOtherAmount);

        String expectedStatement = "Statement for " + this.dummyCustomerName + "\n\n" +
                "Checking Account\n" +
                "  deposit " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "  withdrawal " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "  deposit " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "  withdrawal " + BankUtils.toDollars(this.dummyOtherAmount) + "\n" +
                "Total " + BankUtils.toDollars(this.dummyAmount - this.dummyOtherAmount) + "\n" +
                "\n" +
                "Total In All Accounts " + BankUtils.toDollars(this.dummyAmount - this.dummyOtherAmount);
        String customerStatement = this.bank.getCustomerStatement(customerId);

        Assert.assertEquals(customerStatement, expectedStatement);
    }

    /**
     * Tests that transferring between accounts of a given customer correctly
     * transfers between the accounts.
     */
    @Test
    public void transferringBetweenAccountsOfCustomerCorrectlyTransfers() {
        int customerId = this.bank.registerCustomer(this.dummyCustomerName);
        int firstAccountId = this.bank.openAccount(customerId, AccountType.CHECKING);
        int secondAccountId = this.bank.openAccount(customerId, AccountType.SAVINGS);

        this.bank.deposit(customerId, firstAccountId, this.dummyAmount);
        this.bank.transfer(customerId, firstAccountId, secondAccountId, this.dummyOtherAmount);

        String expectedStatement = "Statement for " + this.dummyCustomerName + "\n\n" +
                "Checking Account\n" +
                "  deposit " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "  withdrawal " + BankUtils.toDollars(this.dummyOtherAmount) + "\n" +
                "Total " + BankUtils.toDollars(this.dummyAmount - this.dummyOtherAmount) + "\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit " + BankUtils.toDollars(this.dummyOtherAmount) + "\n" +
                "Total $5,000.00\n" +
                "\n" +
                "Total In All Accounts " + BankUtils.toDollars(this.dummyAmount);
        String customerStatement = this.bank.getCustomerStatement(customerId);

        Assert.assertEquals(customerStatement, expectedStatement);
    }

    /**
     * Tests that transferring between accounts of different customers correctly
     * transfers between the accounts.
     */
    @Test
    public void transferringBetweenAccountsOfDifferentCustomersCorrectlyTransfers() {
        int firstCustomerId = this.bank.registerCustomer(this.dummyCustomerName);
        int firstAccountId = this.bank.openAccount(firstCustomerId, AccountType.CHECKING);

        int secondCustomerId = this.bank.registerCustomer(this.dummyOtherCustomerName);
        int secondAccountId = this.bank.openAccount(secondCustomerId, AccountType.CHECKING);

        this.bank.deposit(firstCustomerId, firstAccountId, this.dummyAmount);
        this.bank.transfer(firstCustomerId, firstAccountId, secondAccountId, this.dummyOtherAmount);

        String expectedFirstCustomerStatement = "Statement for " + this.dummyCustomerName + "\n\n" +
                "Checking Account\n" +
                "  deposit " + BankUtils.toDollars(this.dummyAmount) + "\n" +
                "  withdrawal " + BankUtils.toDollars(this.dummyOtherAmount) + "\n" +
                "Total " + BankUtils.toDollars(this.dummyAmount - this.dummyOtherAmount) + "\n" +
                "\n" +
                "Total In All Accounts " + BankUtils.toDollars(this.dummyAmount - this.dummyOtherAmount);
        String firstCustomerStatement = this.bank.getCustomerStatement(firstCustomerId);

        Assert.assertEquals(firstCustomerStatement, expectedFirstCustomerStatement);

        String expectedSecondCustomerStatement = "Statement for " + this.dummyOtherCustomerName + "\n\n" +
                "Checking Account\n" +
                "  deposit " + BankUtils.toDollars(this.dummyOtherAmount) + "\n" +
                "Total " + BankUtils.toDollars(this.dummyOtherAmount) + "\n" +
                "\n" +
                "Total In All Accounts " + BankUtils.toDollars(this.dummyOtherAmount);
        String secondtCustomerStatement = this.bank.getCustomerStatement(secondCustomerId);

        Assert.assertEquals(secondtCustomerStatement, expectedSecondCustomerStatement);
    }

    /**
     * Tests that getting the total interest paid to customers gets the total interest
     * paid to customers.
     */
    @Test
    public void gettingTotalInterestPaidGetsTotalInterestPaid() {
        int customerId = this.bank.registerCustomer(this.dummyCustomerName);
        int accountId = this.bank.openAccount(customerId, AccountType.CHECKING);

        this.bank.deposit(customerId, accountId, this.dummyAmount);
        this.bank.deposit(customerId, accountId, this.dummyAmount);
        this.bank.withdraw(customerId, accountId, this.dummyOtherAmount);

        // It's worth noting that we are not mocking any of our date providers in this test.
        // Hence all the transactions made will have the same date, i.e. today. Since the interest on accounts
        // is accrued and compounded daily (as opposed to e.g. hourly), the interest paid will be
        // zero - as all the transactions have been made today. This, perhaps, makes this test redundant.
        double expectedInterestPaid = 0.0;
        double interestPaid = this.bank.getTotalInterestPaid();

        Assert.assertEquals(interestPaid, expectedInterestPaid, 0.001);
    }
}
