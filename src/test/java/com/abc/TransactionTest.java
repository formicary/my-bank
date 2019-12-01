package com.abc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void testTransactionLastNumOfDays() {

        long dayInMs = 1000 * 60 * 60 * 24;
        Date day7 = new Date(System.currentTimeMillis() - (7 * dayInMs));
        Date day10 = new Date(System.currentTimeMillis() - (10 * dayInMs));
        Date day11 = new Date(System.currentTimeMillis() - (11 * dayInMs));

        Account checkingAccount = new CheckingAccount();

        checkingAccount.deposit(1000, day11);
        checkingAccount.deposit(1000, day10);
        checkingAccount.deposit(1000, day7);

        Date now = new Date(System.currentTimeMillis());

        ArrayList<Transaction> transactions = checkingAccount.getTransactionsBetween(day10,now);

        assertEquals(2, transactions.size());
    }

}
