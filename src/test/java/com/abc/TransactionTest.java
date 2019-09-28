package com.abc;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayName("Testing a transaction")
public class TransactionTest {

    Transaction mockTWithdrawel;
    Transaction mockTDeposit;

    @BeforeEach
    void init() {

        mockTDeposit = new Transaction(1000);
        mockTWithdrawel = new Transaction(-1000);
    }

    @Nested
    @DisplayName("When a transaction is initialised")
    class InitTransactionTest {

        @Test
        @DisplayName("a deposit of 1000 should contain an amount: 1000")
        public void testInitAmountDep() {
            double expected = 1000;
            double actual = mockTDeposit.getAmount();
            assertEquals(expected, actual,
                    () -> "Transaction amount expected: " + expected + " Actual: " + actual);
        }

        @Test
        @DisplayName("A withdrawal of 1000 should contain amoount of -1000")
        public void testInitAmountwith() {
            double expected = -1000;
            double actual = mockTWithdrawel.getAmount();
            assertEquals(expected, actual,
                    () -> "Transaction amount expected: " + expected + " Actual: " + actual);

        }

        @Test
        @DisplayName("The date should be the date of initilisation")
        public void testInitDate() {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String actual = mockTDeposit.getTransactionDate().format(format);
            String expected = LocalDateTime.now().format(format);

            assertEquals(expected, actual, () -> "expected date: " + expected + " actual date: " + actual);
        }

        @Test
        @DisplayName("The date should be the date specified, when specified")
        public void testInitDateSpec() {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            mockTDeposit = new Transaction(1000, LocalDateTime.now().plusDays(10));
            String actual = mockTDeposit.getTransactionDate().format(format);
            String expected = LocalDateTime.now().plusDays(10).format(format);

            assertEquals(expected, actual, () -> "expected date: " + expected + " actual date: " + actual);

        }

        @Test
        @DisplayName("The type should be correctly outputed, WITHDRAWAL = 0, DEPOSIT = 1")
        public void testTransType() {

            assertEquals(0, mockTWithdrawel.getTransactionType());
            assertEquals(1, mockTDeposit.getTransactionType());
        }
    }

    @Test
    @DisplayName("When toString is used, should output type and amount")
    public void testToString(){
        assertEquals("Withdrawal $1,000.00", mockTWithdrawel.toString());
        assertEquals("Deposit $1,000.00", mockTDeposit.toString());
    }
}
