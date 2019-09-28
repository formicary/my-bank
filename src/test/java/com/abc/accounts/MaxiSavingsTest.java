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
@DisplayName("Testing a maxi-savings account")
public class MaxiSavingsTest {

    private MaxiSavings mockAcc;

    @BeforeEach
    public void init() {
        mockAcc = new MaxiSavings();
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
        @DisplayName("Should have interest rate of 0.05")
        void testInitIntRate() {
            double actual = mockAcc.intRate;
            double expected = 0.05;
            assertEquals(expected, actual, () -> "Expected intRate: " + expected + " Actual intRate: " + actual);
        }

        @Test
        @DisplayName("Should have second interest rate of 0.001")
        void testInitSecIntRate() {
            double actual = mockAcc.secIntRate;
            double expected = 0.001;
            assertEquals(expected, actual, () -> "Expected intRate: " + expected + " Actual: " + actual);
        }

        @Test
        @DisplayName("Should have accrue rate of 1.3698630136986303E-4")
        void testInitAccrueRate() {
            double actual = mockAcc.accrueRate;
            double expected = 1.3698630136986303E-4;
            assertEquals(expected, actual, () -> "Expected accrueRate: " + expected + " Actual: " + actual);
        }

        @Test
        @DisplayName("Should have second accrue rate of 2.7397260273972604E-6")
        void testInitSecAccrueRate() {
            double actual = mockAcc.secAccrueRate;
            double expected = 2.7397260273972604E-6;
            assertEquals(expected, actual, () -> "Expected secAccrueRate: " + expected + " Actual: " + actual);
        }

        @Test
        @DisplayName("Should have balance of 0.0")
        void testInitBal() {
            double actual = mockAcc.balance;
            assertEquals(0.0, actual, () -> "Expected balance = 0.0, Actual: " + actual);
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
            mockAcc = new MaxiSavings(expected);
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
        @DisplayName("The interest rate should accrue by 1.3698630136986303E-4")
        public void testAccrueInTenDays() {

            mockAcc.updateAccount(mockAcc.dateOfLastUpdate.plusDays(10));
            double expected = 0.051369863013698655;
            double actual = mockAcc.intRate;
            assertEquals(expected, actual, () -> "Expected rate: " + expected + " Actual rate: " + actual);
        }

        @Test
        @DisplayName("The second interest rate should accrue by 2.7397260273972604E-6")
        public void testSecAccrueInTenDays() {

            mockAcc.updateAccount(mockAcc.dateOfLastUpdate.plusDays(10));
            double expected = 0.0010273972602739736;
            double actual = mockAcc.secIntRate;
            assertEquals(expected, actual, () -> "Expected rate: " + expected + " Actual rate: " + actual);
        }

        @Test
        @DisplayName("A balance of 100 should compound to 105 in one day")
        public void testCompoundOneDayFirstRate() {

            mockAcc.balance = 100;
            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(1);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate,
                    () -> "update Account is not working as expected \n " +
                            "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);
            double expected = 105;
            double actual = mockAcc.balance;
            assertEquals(expected, actual, () -> "Expected balance: " + expected + " Actual balance: " + actual);
        }

        @Test
        @DisplayName("perform a check for withdrawal which should return true")
        public void testWithdrawalCheckTrue() {
            mockAcc.withdraw(100);
            mockAcc.deposit(300);
            mockAcc.updateAccount(mockAcc.dateOfLastUpdate.plusDays(5));
            assertTrue(mockAcc.hadWithdrawalInPast(10),
                    () -> "Has falsely found no withdrawal when account contains one 5 days ago");
        }

        @Test
        @DisplayName("Perform a check for withdrawal which should return false")
        public void testWithdrawalCheckFalse() {
            mockAcc.withdraw(100);
            mockAcc.deposit(500, mockAcc.dateOfLastUpdate.plusDays(6));
            mockAcc.updateAccount(mockAcc.dateOfLastUpdate.plusDays(5));

            assertFalse(mockAcc.hadWithdrawalInPast(10),
                    () -> "Has falsely found a withdrawal within 10 days when account added one 11 days ago");
        }

        @Test
        @DisplayName("A balance of 1000 should update to 1638.4817637317944 in ten days with no withdrawal in-between")
        public void testUpdateBalInTenDaysNoWithdrawal() {

            mockAcc.deposit(1000);
            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(10);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate,
                    () -> "update Account is not working as expected \n " +
                            "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);


            double expected = 1638.4817637317944;
            double actual = mockAcc.balance;
            assertEquals(expected, actual, () -> "Expected balance: " + expected + " Actual balance: " + actual);
        }

        @Test
        @DisplayName("A balance of 1000 should update to 1183.962338920174 in ten days with a withdrawal occurring 5 days in")
        public void testUpdateBalInTenDaysWithWithdrawal() {

            mockAcc.deposit(1000);
            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(5);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate,
                    () -> "update Account is not working as expected \n " +
                            "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);

            mockAcc.withdraw(100, newDate);
            mockAcc.updateAccount(newDate.plusDays(5));

            double expected = 1183.962338920174;
            double actual = mockAcc.balance;
            assertEquals(expected, actual, () -> "Expected balance: " + expected + " Actual balance: " + actual);
        }

        @Test
        @DisplayName("There should be 638.4817637317944 interest earned at balance: 1000 and ten days passed")
        public void testTotalInterestEarnedTenDays() {

            mockAcc.deposit(1000);

            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(10);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate, () -> "update Account is not working as expected \n " +
                    "Expected interest earned: " + newDate + "Actual: " + mockAcc.dateOfLastUpdate);


            double expected = 638.4817637317947;
            double actual = mockAcc.totalInterestEarned();

            assertEquals(expected, actual, () -> "Expected balance: " + expected + " Actual balance: " + actual);
        }

        @Test
        @DisplayName("There should be #### interest earned at balance: 1000, twenty days passed, and a withdrawal: 100 10 days in")
        public void testTotalInterestEarnedTwentyDays() {

            mockAcc.deposit(1000);

            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(10);

            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate == newDate, () -> "update Account is not working as expected \n " +
                    "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);

            mockAcc.withdraw(100, newDate);
            mockAcc.updateAccount(newDate.plusDays(10));

            double expected = 654.5528079966539;
            double actual = mockAcc.totalInterestEarned();

            assertEquals(expected, actual, () -> "Expected balance: " + expected + " Actual balance: " + actual);
        }

    }

    @Nested
    @DisplayName("When displaying a report")
    class statementTest {

        @Test
        @DisplayName("Should correctly display an empty statement in Dollar format")
        public void testEmptyStatementInDollars() {

            String expected = "Maxi Savings Account\n" +
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

            String expected = "Maxi Savings Account\n" +
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
    @DisplayName("When calling toString, output should be Maxi Savings Account")
    public void testToString() {
        assertEquals("Maxi Savings Account", mockAcc.toString());
    }

}
