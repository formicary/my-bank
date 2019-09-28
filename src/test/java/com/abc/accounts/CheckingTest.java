package com.abc.accounts;

import com.abc.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * @project MyBank
 */
@DisplayName("Testing a checking account")
public class CheckingTest {

    private Checking mockAcc;

    @BeforeEach
    public void init() {
        mockAcc = new Checking();
    }

    @Nested
    @DisplayName("When an account is initialised it")
    class InitAccountTest {

        @Test
        @DisplayName("should contain no transactions")
        void testInitTransaction() {
            int numbOftrans = mockAcc.transactions.size();
            assertEquals(0, numbOftrans, () -> "Should contain no transactions but contains: " + numbOftrans);
        }

        @Test
        @DisplayName("Should have interest rate of 0.001")
        void testInitIntRate() {
            double actual = mockAcc.intRate;
            double expected = 0.001;
            assertEquals(expected, actual, () -> "Expected intRate = " + expected + " Actual = " + actual);
        }

        @Test
        @DisplayName("Should have accrue rate of 2.7397260273972604E-6")
        void testInitAccrueRate() {
            double actual = mockAcc.accrueRate;
            double expected = 2.7397260273972604E-6;
            assertEquals(expected, actual, () -> "Expected accrueRate = " + expected + " Actual = " + actual);
        }

        @Test
        @DisplayName("Should have balance of 0.0")
        void testInitBal() {
            double actual = mockAcc.balance;
            assertEquals(0.0, actual, () -> "Expected balance = 0.0, Actual = " + actual);
        }

        @Test
        @DisplayName("Should contain date of initialisation when date is not specified")
        void testInitDate() {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String actual = mockAcc.dateOfLastUpdate.format(format);
            String expected = LocalDateTime.now().format(format);

            assertEquals(expected, actual, () -> "expected date: " + expected + " actual date: " + actual);
        }

        @Test
        @DisplayName("Should contain the date that is specified, when specified")
        void testInitSpecifiedDate() {
            LocalDateTime expected = LocalDateTime.now();
            mockAcc = new Checking(expected);
            LocalDateTime actual = mockAcc.dateOfLastUpdate;
            assertEquals(expected, actual, () -> "Expected date: " + expected + " Actual date: " + actual);
        }
    }

    @Nested
    @DisplayName("When making a deposit")
    class DepositTest {

        @Test
        @DisplayName("of 2000.50, balance must equal 2000.50")
        void testSingleDeposit() {
            double expected = 2000.50;
            mockAcc.deposit(expected);
            double actual = mockAcc.balance;
            assertEquals(expected, actual, () -> "Expected balance: " + expected + "Actual balance: " + actual);
        }

        @Test
        @DisplayName("of 200.40, then 500.70, then 300 then balance must be 1001.10")
        void testMultipleDeposits() {
            double expected = 1001.10;
            mockAcc.deposit(200.40);
            mockAcc.deposit(500.70);
            mockAcc.deposit(300);

            double actual = mockAcc.balance;
            assertEquals(expected, actual, () -> "Expected balance: " + expected + "Actual balance: " + actual);
        }

        @Test
        @DisplayName("of -500, should throw an IllegalArgumentException")
        public void testDepositThrows() {
            assertThrows(IllegalArgumentException.class, () -> mockAcc.deposit(-500));
        }
    }

    @Nested
    @DisplayName("When making a withdrawal")
    class WithdrawalTest {


        @Test
        @DisplayName("of 500.50, balance must equal -500.50")
        public void testSingleWithdrawal() {
            double expected = -500.50;
            mockAcc.withdraw(-expected);
            double actual = mockAcc.getBalance();
            assertEquals(expected, actual, () -> "Expected balance: " + expected + "Actual balance: " + actual);
        }

        @Test
        @DisplayName("of 200.40, then 500.70, then 300 then balance must be -1001.10")
        void testMultipleWithdrawals() {
            double expected = -1001.10;
            mockAcc.withdraw(200.40);
            mockAcc.withdraw(500.70);
            mockAcc.withdraw(300);

            double actual = mockAcc.balance;
            assertEquals(expected, actual, () -> "Expected balance: " + expected + "Actual balance: " + actual);
        }

        @Test
        @DisplayName("of -500, should throw an IllegalArgumentException")
        public void testWithdrawThrows() {
            assertThrows(IllegalArgumentException.class, () -> mockAcc.withdraw(-500));
        }
    }

    @Nested
    @DisplayName("Concerning transactions where")
    class transactionsTest {

        @Test
        @DisplayName("a transaction is added, the account should contain it")
        public void testAddTransaction() {
            Transaction expected = new Transaction(500);
            mockAcc.addTransaction(expected);
            assertTrue(mockAcc.getTransactions().contains(expected),
                    () -> "Account does not contain the transaction: " + expected);
        }

        @Test
        @DisplayName("multiple are added with a combined sum of 505.56, sumTransactions should output 505.56")
        public void testSumTransitions() {


            mockAcc.addTransaction(new Transaction(1000));
            assumeTrue(mockAcc.transactions.size() == 1);
            mockAcc.addTransaction(new Transaction(-500));
            mockAcc.addTransaction(new Transaction(5.56));

            double expected = 505.56;
            double actual = mockAcc.sumTransactions();

            assertEquals(expected, actual, () -> "Expected sum: " + expected + " Actual: " + actual);
        }
    }

    @Nested
    @DisplayName("When updating the account")
    class UpdateAccountTest {

        @Test
        @DisplayName("The date should equal the date that the account was updated")
        public void testUpdateDate() {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            mockAcc.updateAccount();
            String actual = mockAcc.dateOfLastUpdate.format(format);
            String expected = LocalDateTime.now().format(format);
            assertEquals(expected, actual, () -> "Expected date: " + expected + "Actual Date: " + actual);
        }

        @Test
        @DisplayName("The date should equal the date specified when updating")
        public void testUpdateDateSpecified() {
            LocalDateTime expected = LocalDateTime.now().plusDays(10);
            mockAcc.updateAccount(expected);
            LocalDateTime actual = mockAcc.dateOfLastUpdate;

            assertEquals(expected, actual, () -> "Expected date: " + expected + " Actual Date: " + actual);
        }

        @Test
        @DisplayName("The rate should accrue 10 times in 10 days")
        public void testAccrueTenDays() {

            mockAcc.updateAccount(mockAcc.dateOfLastUpdate.plusDays(10));
            double expected = 0.0010273972602739736;
            double actual = mockAcc.intRate;
            assertEquals(expected, actual, () -> "Expected rate: " + expected + " Actual rate: " + actual);
        }

        @Test
        @DisplayName("The balance should compound from 1000 to 1001 after 1 day")
        public void testCompoundOneDay() {
            mockAcc.balance = 1000;
            mockAcc.updateAccount(mockAcc.dateOfLastUpdate.plusDays(1));
            double expected = 1001;
            double actual = mockAcc.balance;
            assertEquals(expected, actual,
                    () -> "Expected balance: " + expected + " actual balance: " + actual);
        }

        @Test
        @DisplayName("A balance of 1000 should accrue and compound to value: 1010.169528502181379 after 10 days")
        public void testAcrueAndCompoundTenDays() {
            mockAcc.deposit(1000);

            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(10);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate, () -> "update Account is not working as expected \n " +
                    "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);


            double expected = 1010.169528502181379;
            double actual = mockAcc.balance;

            assertEquals(expected, actual, () -> "Expected interest: " + expected + " Actual interest: " + actual);
        }

        @Test
        @DisplayName("There should be 10.169528502181379 interest earned at balance: 1000 and ten days passed")
        public void testTotalInterestEarnedTenDays() {

            mockAcc.deposit(1000);

            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(10);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate, () -> "update Account is not working as expected \n " +
                    "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);


            double expected = 10.169528502181308;
            double actual = mockAcc.totalInterestEarned();

            assertEquals(expected, actual, () -> "Expected interest: " + expected + " Actual interest: " + actual);
        }

        @Test
        @DisplayName("There should be 10.169528502181379 interest earned at balance: 1000 and ten days passed")
        public void testTotalInterestEarnedTwentyDays() {

            mockAcc.deposit(2000);

            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(20);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate, () -> "update Account is not working as expected \n " +
                    "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);

            double expected = 41.443602524865305;
            double actual = mockAcc.totalInterestEarned();

            assertEquals(expected, actual, () -> "Expected interest: " + expected + " Actual interest: " + actual);
        }

    }

    @Nested
    @DisplayName("When displaying a report")
    class statementTest {

        @Test
        @DisplayName("Should correctly display an empty statement in Dollar format")
        public void testEmptyStatementInDollars() {

            String expected = "Checking Account\n" +
                    "Total $0.00";
            String actual = mockAcc.getStatementInDollars();

            assertEquals(expected, actual,
                    () -> "Expected statement: " + expected + " Actual statement: " + actual);
        }

        @Test
        @DisplayName("Should correctly display deposits and withdrawals in Dollar format")
        public void testStatementInDollars() {
            mockAcc.deposit(1000);
            mockAcc.deposit(500);
            mockAcc.withdraw(200);

            String expected = "Checking Account\n" +
                    "  Deposit $1,000.00\n" +
                    "  Deposit $500.00\n" +
                    "  Withdrawal $200.00\n" +
                    "Total $1,300.00";

            String actual = mockAcc.getStatementInDollars();
            assertEquals(expected, actual,
                    () -> "Expected statement: \n" + expected + " Actual statement: \n" + actual);
        }
    }

    @Test
    @DisplayName("When calling toString output should be Checking Account")
    public void testToString() {
        assertEquals("Checking Account", mockAcc.toString());
    }

}