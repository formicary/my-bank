package com.abc;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    LocalDateTime now = LocalDateTime.now();
    @Test
    public void testTransfer() {
        Account first = new CheckingAccount();
        first.deposit(5000);
        Account second = new SavingsAccount();
        second.deposit(10);
        Thread firstT = new Thread(new Runnable() {
            @Override
            public void run() {
                first.transferTo(300, second);
            }
        });
        Thread secondT = new Thread(new Runnable() {
            @Override
            public void run() {
                second.transferTo(5, first);
            }
        });
        firstT.run();
        secondT.run();
        firstT.run();
        secondT.run();
        assertEquals(4410, first.totalAmount, DOUBLE_DELTA);
    }
    @Test
    public void testCheckingAccountInterestEarnedDaily() {
        Account first = new CheckingAccount();
        first.deposit(1500);
        assertEquals(1.5, first.interestEarned(), DOUBLE_DELTA);

        first = new CheckingAccount();

        first.transactions.add(new Transaction(500, now));
        first.transactions.add(new Transaction(1000, now.minusDays(1)));

        assertEquals(1.501, first.interestEarnedDaily(), DOUBLE_DELTA);
    }
    @Test
    public void testSavingsAccountInterestEarnedDaily() {
        Account savings = new SavingsAccount();
        savings.transactions.add(new Transaction(1000, now));
        savings.transactions.add(new Transaction(2000, now.minusDays(1)));
        // 2000 -> 3, 1003 -> 1 + 0.006
        assertEquals(4.006, savings.interestEarnedDaily(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsInterestEarnedDaily() {
        Account maxi = new MaxiSavingsAccount();
        maxi.transactions.add(new Transaction(5000, now.minusDays(20)));
        maxi.deposit(1000);
        maxi.withdraw(500);
        //5000 -> 250, 750 -> 0.75
        assertEquals(250.75, maxi.interestEarnedDaily(), DOUBLE_DELTA);
    }
}
