package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class CustomerTest {
    private static final double EPSILON = 1e-15;
    private static final int BIG_DECIMAL_SCALE = 10;

    @Test
    public void getFullName() {
        final String FIRST_NAME = "Henry", SURNAME = "Hamster";
        Customer customer = new Customer(SURNAME, FIRST_NAME);
        final String RETURNED_VALUE = customer.getFullName();
        assertEquals(
                "Test that first N chars make up the customer\'s first name",
                FIRST_NAME,
                RETURNED_VALUE.substring(0, FIRST_NAME.length()));
        assertEquals(
                "Test that first and surname are separated by space",
                ' ',
                RETURNED_VALUE.charAt(FIRST_NAME.length()));
        assertEquals(
                "Test that result ends with the customer\'s surname",
                SURNAME,
                RETURNED_VALUE.substring(RETURNED_VALUE.length() - SURNAME.length()));
    }

    @Test
    public void addAccount() {
        final double DEPOSIT_AMOUNT = 5.50;

        CheckingAccount account = new CheckingAccount();
        account.deposit(DEPOSIT_AMOUNT);
        Customer customer = new Customer("Doe", "John");
        final int ACCOUNT_ID = account.getId();
        final int RETURNED_ID = customer.addAccount(account);
        assertEquals("Test that method returns ID of account", ACCOUNT_ID, RETURNED_ID);

        final Account RETURNED_ACCOUNT = customer.getSingleAccount(ACCOUNT_ID);
        assertEquals(
                "Test that transactions are added with account",
                DEPOSIT_AMOUNT,
                RETURNED_ACCOUNT.getBalance(),
                EPSILON);

        SavingsAccount secondAccount = new SavingsAccount();
        customer.addAccount(secondAccount);
        final int EXPECTED_NUMBER_OF_ACCOUNTS = 2,
                NUMBER_OF_ACCOUNTS = customer.getAccounts().size();
        assertEquals(
                "Test that multiple accounts can be added",
                EXPECTED_NUMBER_OF_ACCOUNTS,
                NUMBER_OF_ACCOUNTS);
    }

    @Test
    public void openCheckingAccount() {
        Customer customer = new Customer("Doe", "Jane");
        customer.openCheckingAccount();
        assertEquals("Test that one new account is added", 1, customer.getAccounts().size());
        assertEquals(
                "Test that account added is a checking account",
                CheckingAccount.class,
                customer.getAccounts().get(0).getClass());
    }

    @Test
    public void openSavingsAccount() {
        Customer customer = new Customer("Doe", "Jane");
        customer.openSavingsAccount();
        assertEquals("Test that one new account is added", 1, customer.getAccounts().size());
        assertEquals(
                "Test that account added is a savings account",
                SavingsAccount.class,
                customer.getAccounts().get(0).getClass());
    }

    @Test
    public void openMaxiSavingsAccount() {
        Customer customer = new Customer("Doe", "Jane");
        customer.openMaxiSavingsAccount();
        assertEquals("Test that one new account is added", 1, customer.getAccounts().size());
        assertEquals(
                "Test that account added is a maxi savings account",
                MaxiSavingsAccount.class,
                customer.getAccounts().get(0).getClass());
    }

    @Test
    public void getNumberOfAccounts() {
        Customer customer = new Customer("Customer", "Happy");
        final int RANDOM_NUMBER_FROM_ONE_TO_TEN = (int) (Math.random() * 10) + 1;
        for (int i = 0; i < RANDOM_NUMBER_FROM_ONE_TO_TEN; i++) customer.openMaxiSavingsAccount();
        assertEquals(
                "Test that returned value is the amount of accounts that the customer has open",
                RANDOM_NUMBER_FROM_ONE_TO_TEN,
                customer.getNumberOfAccounts());
    }

    @Test
    public void totalInterestEarned() {
        final int DAYS_ACCRUING_INTEREST_MAXI = 4;
        final double MAXI_DEPOSIT = 5000;

        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(MAXI_DEPOSIT);
        maxiSavingsAccount
                .getTransactions()
                .get(0)
                .setTransactionDate(ZonedDateTime.now().minusDays(DAYS_ACCRUING_INTEREST_MAXI));

        final int DAYS_ACCRUING_INTEREST_CHECKING = 2;
        final double CHECKING_DEPOSIT = 2200;
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.deposit(CHECKING_DEPOSIT);
        checkingAccount
                .getTransactions()
                .get(0)
                .setTransactionDate(ZonedDateTime.now().minusDays(DAYS_ACCRUING_INTEREST_CHECKING));

        Customer customer = new Customer("Smith", "John");
        customer.addAccount(maxiSavingsAccount);
        customer.addAccount(checkingAccount);
        final BigDecimal EXPECTED_INTEREST_EARNED =
                maxiSavingsAccount.interestEarned().add(checkingAccount.interestEarned());

        assertEquals(
                "Test that result is cumulative interest earned on all of customer\'s accounts",
                EXPECTED_INTEREST_EARNED.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP),
                customer.totalInterestEarned().setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        Customer withoutAccounts = new Customer("Accounts", "No");
        assertEquals(
                "Test that result is zero when customer has no accounts",
                BigDecimal.valueOf(0),
                withoutAccounts.totalInterestEarned());
    }

    @Test
    public void allStatements() {
        Customer customerWithNone = new Customer("Accounts", "No");
        Customer customerWithOne = new Customer("Account", "One");
        Customer customerWithThree = new Customer("Accounts", "Three");
        CheckingAccount checkingAccountOne = new CheckingAccount();
        CheckingAccount checkingAccountTwo = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();
        final double ACCOUNT_ONE_DEPOSIT = 200,
                ACCOUNT_TWO_DEPOSIT = 500,
                ACCOUNT_THREE_DEPOSIT = 50.50,
                ACCOUNT_THREE_WITHDRAW = 20;
        checkingAccountOne.deposit(ACCOUNT_ONE_DEPOSIT);
        checkingAccountTwo.deposit(ACCOUNT_TWO_DEPOSIT);
        savingsAccount.deposit(ACCOUNT_THREE_DEPOSIT);
        savingsAccount.withdraw(ACCOUNT_THREE_WITHDRAW);

        customerWithOne.addAccount(checkingAccountOne);
        customerWithThree.addAccount(checkingAccountOne);
        customerWithThree.addAccount(checkingAccountTwo);
        customerWithThree.addAccount(savingsAccount);

        String withNoneResult = customerWithNone.allStatements();
        String withOneResult = customerWithOne.allStatements();
        String withThreeResult = customerWithThree.allStatements();

        assertTrue(
                "Test that customer name is displayed for customer with no accounts",
                withNoneResult.contains(customerWithNone.getFullName()));
        assertTrue(
                "Test that customer name is displayed for customer with one account",
                withOneResult.contains(customerWithOne.getFullName()));
        assertTrue(
                "Test that customer name is displayed for customer with multiple accounts",
                withThreeResult.contains(customerWithThree.getFullName()));

        final String PLURAL = "accounts";
        assertFalse(
                "Test that plural is NOT used for customer with one account",
                withOneResult.contains(PLURAL));
        assertTrue(
                "Test that plural IS used for customer with multiple accounts",
                withThreeResult.contains(PLURAL));

        assertTrue(
                "Test that all account statements are included in results",
                withThreeResult.contains(checkingAccountOne.getStatement())
                        && withThreeResult.contains(checkingAccountTwo.getStatement())
                        && withThreeResult.contains(savingsAccount.getStatement()));

        final double WITH_THREE_EXPECTED_BALANCE =
                ACCOUNT_ONE_DEPOSIT
                        + ACCOUNT_TWO_DEPOSIT
                        + ACCOUNT_THREE_DEPOSIT
                        - ACCOUNT_THREE_WITHDRAW;
        assertTrue(
                "Test that result contains the combined balance across all accounts",
                withThreeResult.contains(Double.toString(WITH_THREE_EXPECTED_BALANCE)));
    }

    @Test
    public void getSingleAccount() {
        Customer customer = new Customer("Customer", "Loyal");
        SavingsAccount savingsAccount = new SavingsAccount();
        int savingsAccountId = savingsAccount.getId();
        customer.addAccount(new MaxiSavingsAccount());
        customer.addAccount(savingsAccount);
        customer.addAccount(new CheckingAccount());
        assertEquals(
                "Test that account returned is that whose id is passed as parameter",
                savingsAccount,
                customer.getSingleAccount(savingsAccountId));

        int invalidId = -65;
        assertNull(
                "Test that null is returned when customer has no account with parameter id",
                customer.getSingleAccount(invalidId));
    }

    @Test
    public void getAccounts() {
        Customer customer = new Customer("Smith", "Jane");
        customer.openCheckingAccount();
        customer.openCheckingAccount();
        customer.openMaxiSavingsAccount();
        customer.openMaxiSavingsAccount();
        customer.openMaxiSavingsAccount();

        int allAccounts = 5, checkingAccounts = 2;
        assertEquals(
                "Test that all accounts are returned when no parameter is passed",
                allAccounts,
                customer.getAccounts().size());
        assertEquals(
                "Test that when a parameter is passed only accounts of that class are returned",
                checkingAccounts,
                customer.getAccounts(CheckingAccount.class).size());
    }

    @Test(expected = NullPointerException.class)
    public void transferBetweenAccounts() {
        final double INITIAL_SENDING_ACCOUNT_BALANCE = 500, INITIAL_RECEIVING_ACCOUNT_BALANCE = 4.50;
        Customer customer = new Customer("Smith", "Jack");

        SavingsAccount sendingAccount = new SavingsAccount();
        int sendingAccountId = sendingAccount.getId();
        sendingAccount.deposit(INITIAL_SENDING_ACCOUNT_BALANCE);
        customer.addAccount(sendingAccount);

        MaxiSavingsAccount receivingAccount = new MaxiSavingsAccount();
        int receivingAccountId = receivingAccount.getId();
        receivingAccount.deposit(INITIAL_RECEIVING_ACCOUNT_BALANCE);
        customer.addAccount(receivingAccount);

        final double TRANSFER_AMOUNT = 42.42;
        customer.transferBetweenAccounts(sendingAccountId, receivingAccountId, TRANSFER_AMOUNT);

        assertEquals("Test that transfer amount is deducted from sending account", INITIAL_SENDING_ACCOUNT_BALANCE-TRANSFER_AMOUNT, sendingAccount.getBalance(), EPSILON);
        assertEquals("Test that transfer amount is added to receiving account", INITIAL_RECEIVING_ACCOUNT_BALANCE + TRANSFER_AMOUNT, receivingAccount.getBalance(), EPSILON);

        assertTrue("Test that transaction appears on sending account statement", sendingAccount.getStatement().contains(Double.toString(TRANSFER_AMOUNT)));
        assertTrue("Test that transaction appears on receiving account  statement", receivingAccount.getStatement().contains(Double.toString(TRANSFER_AMOUNT)));

        final int INVALID_ID = -5;
        customer.transferBetweenAccounts(INVALID_ID, receivingAccountId, TRANSFER_AMOUNT);
    }
}
