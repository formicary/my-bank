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
@DisplayName("Testing a normal account")
public class AccountTest {

    private static Account mockAcc;

    @BeforeEach
    public void init() {

        LocalDateTime date = LocalDateTime.now();
        mockAcc = new Account(date) {
            @Override
            protected void compoundBalance() {

            }

            @Override
            protected void accrueInterest() {

            }
        };
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
        @DisplayName("Should have interest rate of 0.0")
        void testInitIntRate() {
            double actual = mockAcc.intRate;
            assertEquals(0.0, actual, () -> "Expected intRate = 0.0, Actual = " + actual);
        }

        @Test
        @DisplayName("Should have accrue rate of 0.0")
        void testInitAccrueRate() {
            double actual = mockAcc.accrueRate;
            assertEquals(0.0, actual, () -> "Expected accrueRate = 0.0, Actual = " + actual);
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
            mockAcc = new Account(expected) {
                @Override
                protected void compoundBalance() {

                }

                @Override
                protected void accrueInterest() {

                }
            };
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
            assumeTrue(mockAcc.transactions.size() == 1, () -> "transitions are not successfully updating");
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
        @DisplayName("There should be no interest earned")
        public void testTotalInterestEarned() {
            mockAcc.deposit(1000);
            mockAcc.deposit(500);
            mockAcc.withdraw(200);

            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(10);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate, () -> "update Account is not working as expected \n " +
                    "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);

            double expected = 0;
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

            String expected = "Account\n" +
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

            String expected = "Account\n" +
                    "  Deposit $1,000.00\n" +
                    "  Deposit $500.00\n" +
                    "  Withdrawal $200.00\n" +
                    "Total $1,300.00";

            String actual = mockAcc.getStatementInDollars();
            assertEquals(expected, actual,
                    () -> "Expected statement: " + expected + " Actual statement: " + actual);
        }
    }

    @Test
    @DisplayName("When calling toString output should be Account")
    void testToString() {
        String expected = "Account";
        String actual = mockAcc.toString();
        assertEquals(expected, actual, () -> "Expected output: " + expected + "Actual output: " + actual);
    }


}
