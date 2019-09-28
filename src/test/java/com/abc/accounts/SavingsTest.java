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
public class SavingsTest {

    private Savings mockAcc;

    @BeforeEach
    public void init(){
        mockAcc = new Savings();
    }

    @Nested
    @DisplayName("When an account is initialised it")
    class InitAccountTest{

        @Test
        @DisplayName("should contain no transactions")
        void testInitTransaction(){
            int numbOftrans = mockAcc.transactions.size();
            assertEquals(0, numbOftrans, () -> "Should contain no transactions but contains: " + numbOftrans);
        }

        @Test
        @DisplayName("Should have interest rate of 0.001")
        void testInitIntRate(){
            double actual = mockAcc.intRate;
            double expected = 0.001;
            assertEquals(expected,actual, () -> "Expected intRate: " + expected + " Actual intRate: " + actual);
        }

        @Test
        @DisplayName("Should have second interest rate of 0.002")
        void testInitSecIntRate(){
            double actual = mockAcc.secIntRate;
            double expected = 0.002;
            assertEquals(expected,actual, () -> "Expected intRate: " + expected + " Actual: " + actual);
        }

        @Test
        @DisplayName("Should have accrue rate of 2.7397260273972604E-6")
        void testInitAccrueRate(){
            double actual = mockAcc.accrueRate;
            double expected =  2.7397260273972604E-6;
            assertEquals(expected,actual, () -> "Expected accrueRate: " + expected +  " Actual: " + actual);
        }

        @Test
        @DisplayName("Should have second accrue rate of 5.479452054794521E-6")
        void testInitSecAccrueRate(){
            double actual = mockAcc.secAccrueRate;
            double expected = 5.479452054794521E-6;
            assertEquals(expected,actual, () -> "Expected secAccrueRate: " + expected + " Actual: " + actual);
        }

        @Test
        @DisplayName("Should have balance of 0.0")
        void testInitBal(){
            double actual = mockAcc.balance;
            assertEquals(0.0,actual, () -> "Expected balance = 0.0, Actual: " + actual);
        }

        @Test
        @DisplayName("Should contain date of initialisation when date is not specified")
        void testInitDate(){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String actual = mockAcc.dateOfLastUpdate.format(format);
            String expected = LocalDateTime.now().format(format);

            assertEquals(expected,actual, () -> "expected date: " + expected + " actual date: " + actual);
        }

        @Test
        @DisplayName("Should contain the date that is specified, when specified")
        void testInitSpecifiedDate(){
            LocalDateTime expected = LocalDateTime.now();
            mockAcc = new Savings(expected);
            LocalDateTime actual = mockAcc.dateOfLastUpdate;
            assertEquals(expected,actual, () -> "Expected date: " + expected + " Actual date: " + actual);
        }
    }

    @Nested
    @DisplayName("When making a deposit")
    class DepositTest{

        @Test
        @DisplayName("of 2000.50, balance must equal 2000.50")
        void testSingleDeposit(){
            double expected = 2000.50;
            mockAcc.deposit(expected);
            double actual = mockAcc.balance;
            assertEquals(expected,actual, () -> "Expected balance: " + expected + "Actual balance: " + actual);
        }

        @Test
        @DisplayName("of 200.40, then 500.70, then 300 then balance must be 1001.10")
        void testMultipleDeposits(){
            double expected = 1001.10;
            mockAcc.deposit(200.40);
            mockAcc.deposit(500.70);
            mockAcc.deposit(300);

            double actual = mockAcc.balance;
            assertEquals(expected, actual, () -> "Expected balance: " + expected + "Actual balance: " + actual);
        }

        @Test
        @DisplayName("of -500, should throw an IllegalArgumentException")
        public void testDepositThrows(){
            assertThrows(IllegalArgumentException.class, () -> mockAcc.deposit(-500));
        }
    }

    @Nested
    @DisplayName("When making a withdrawal")
    class WithdrawalTest{


        @Test
        @DisplayName("of 500.50, balance must equal -500.50")
        public void testSingleWithdrawal(){
            double expected = -500.50;
            mockAcc.withdraw(-expected);
            double actual = mockAcc.getBalance();
            assertEquals(expected,actual, () -> "Expected balance: " + expected + "Actual balance: " + actual);
        }

        @Test
        @DisplayName("of 200.40, then 500.70, then 300 then balance must be -1001.10")
        void testMultipleWithdrawals(){
            double expected = -1001.10;
            mockAcc.withdraw(200.40);
            mockAcc.withdraw(500.70);
            mockAcc.withdraw(300);

            double actual = mockAcc.balance;
            assertEquals(expected, actual, () -> "Expected balance: " + expected + "Actual balance: " + actual);
        }

        @Test
        @DisplayName("of -500, should throw an IllegalArgumentException")
        public void testWithdrawThrows(){
            assertThrows(IllegalArgumentException.class, () -> mockAcc.withdraw(-500));
        }
    }

    @Nested
    @DisplayName("Concerning transactions where")
    class transactionsTest{

        @Test
        @DisplayName("a transaction is added, the account should contain it")
        public void testAddTransaction(){
            Transaction expected = new Transaction(500);
            mockAcc.addTransaction(expected);
            assertTrue(mockAcc.getTransactions().contains(expected),
                    () -> "Account does not contain the transaction: " + expected );
        }

        @Test
        @DisplayName("multiple are added with a combined sum of 505.56, sumTransactions should output 505.56")
        public void testSumTransitions(){


            mockAcc.addTransaction(new Transaction(1000));
            assumeTrue(mockAcc.transactions.size()==1);
            mockAcc.addTransaction(new Transaction(-500));
            mockAcc.addTransaction(new Transaction(5.56));

            double expected = 505.56;
            double actual = mockAcc.sumTransactions();

            assertEquals(expected, actual, () -> "Expected sum: " + expected + " Actual: " + actual);
        }
    }

    @Nested
    @DisplayName("When updating the account")
    class UpdateAccountTest{

        @Test
        @DisplayName("The date should equal the date that the account was updated")
        public void testUpdateDate(){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            mockAcc.updateAccount();
            String actual = mockAcc.dateOfLastUpdate.format(format);
            String expected = LocalDateTime.now().format(format);
            assertEquals(expected,actual, () -> "Expected date: " + expected + "Actual Date: "+ actual);
        }

        @Test
        @DisplayName("The date should equal the date specified when updating")
        public void testUpdateDateSpecified(){
            LocalDateTime expected = LocalDateTime.now().plusDays(10);
            mockAcc.updateAccount(expected);
            LocalDateTime actual = mockAcc.dateOfLastUpdate;

            assertEquals(expected,actual, () -> "Expected date: " + expected + " Actual Date: "+ actual);
        }

        @Test
        @DisplayName("The interest rate should accrue by 2.7397260273972604E-6")
        public void testAccrueInTenDays(){

            mockAcc.updateAccount(mockAcc.dateOfLastUpdate.plusDays(10));
            double expected = 0.0010273972602739736;
            double actual = mockAcc.intRate;
            assertEquals(expected,actual, () -> "Expected rate: " + expected + " Actual rate: "+ actual);
        }

        @Test
        @DisplayName("The second interest rate should accrue by 5.479452054794521E-6")
        public void testSecAccrueInTenDays(){

            mockAcc.updateAccount(mockAcc.dateOfLastUpdate.plusDays(10));
            double expected = 0.002054794520547947;
            double actual = mockAcc.secIntRate;
            assertEquals(expected,actual, () -> "Expected rate: " + expected + " Actual rate: "+ actual);
        }

        @Test
        @DisplayName("A balance of 100 should compound to 100.1 in one day")
        public void testCompoundOneDayFirstRate(){

            mockAcc.balance = 100;
            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(1);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate==newDate,
                    () -> "update Account is not working as expected \n " +
                            "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);
            double expected = 100.1;
            double actual = mockAcc.balance;
            assertEquals(expected,actual, () ->  "Expected balance: " + expected + " Actual balance: "+ actual);
        }

        @Test
        @DisplayName("A balance of 1001 should compound to 1002.002 in one day")
        public void testCompoundOneDaySecRate(){
            mockAcc.balance = 1001;
            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(1);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate==newDate,
                    () -> "update Account is not working as expected \n " +
                            "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);
            double expected = 1002.002;
            double actual = mockAcc.balance;
            assertEquals(expected,actual, () ->  "Expected balance: " + expected + " Actual balance: "+ actual);
        }

        @Test
        @DisplayName("A balance of 1000 should accrue and compound to 1020.925549651078 in twenty days")
        public void testAccrueAndCompTenDays(){

            mockAcc.deposit(1000);
            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(20);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate==newDate,
                    () -> "update Account is not working as expected \n " +
                    "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);


            double expected = 1020.925549651078;
            double actual = mockAcc.balance;
            assertEquals(expected,actual, () -> "Expected balance: " + expected + " Actual balance: " + actual);
        }

        @Test
        @DisplayName("There should be 10.169528502181379 interest earned at balance: 1000 and ten days passed")
        public void testTotalInterestEarnedTenDays(){

            mockAcc.deposit(1000);

            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(10);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate==newDate, () -> "update Account is not working as expected \n " +
                    "Expected interest earned: " + newDate + "Actual: " + mockAcc.dateOfLastUpdate);


            double expected = 10.21601964171533;
            double actual = mockAcc.totalInterestEarned();

            assertEquals(expected,actual, () -> "Expected balance: " + expected + " Actual balance: " + actual);
        }

        @Test
        @DisplayName("There should be 20.731919009771445 interest earned at balance: 995 and ten days passed")
        public void testTotalInterestEarnedTwentyDays(){

            mockAcc.deposit(995);

            LocalDateTime oldDate = mockAcc.dateOfLastUpdate;
            LocalDateTime newDate = oldDate.plusDays(20);
            mockAcc.updateAccount(newDate);
            assumeTrue(mockAcc.dateOfLastUpdate==newDate, () -> "update Account is not working as expected \n " +
                    "Date expected: " + newDate + "Date actual: " + mockAcc.dateOfLastUpdate);

            double expected = 20.731919009771595;
            double actual = mockAcc.totalInterestEarned();

            assertEquals(expected,actual, () -> "Expected balance: " + expected + " Actual balance: " + actual);
        }

    }

    @Nested
    @DisplayName("When displaying a report")
    class statementTest{

        @Test
        @DisplayName("Should correctly display an empty statement in Dollar format")
        public void testEmptyStatementInDollars(){

            String expected = "Savings Account\n" +
                    "Total $0.00";
            String actual = mockAcc.getStatementInDollars();

            assertEquals(expected,actual,
                    () -> "Expected statement: " + expected + " Actual statement: " + actual);
        }

        @Test
        @DisplayName("Should correctly display deposits and withdrawals in Dollar format")
        public void testStatementInDollars(){
            mockAcc.deposit(1000);
            mockAcc.deposit(500);
            mockAcc.withdraw(200);

            String expected = "Savings Account\n" +
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
    public void testToString(){
        assertEquals("Savings Account", mockAcc.toString());
    }

}
