package com.abc;

import com.abc.helper.Transactions;
import com.abc.transaction.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void dateSearchExact() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(500, new Date(2018, 10, 9)));

        Transaction expectedMatch = new Transaction(800, new Date(2018, 9, 9));
        transactions.add(expectedMatch);
        transactions.add(new Transaction(500, new Date(2018, 8, 9)));
        transactions.add(new Transaction(500, new Date(2018, 8, 10)));

        List<Transaction> matches = Transactions.dateSearch(transactions, new Date(2018, 9, 9));

        assertEquals(matches.get(0), expectedMatch);

    }

    @Test
    public void dateSearchRange() {

        ArrayList<Transaction> transactions = new ArrayList<>();

        Transaction notExpected = new Transaction(500, new Date(2018, 10, 9));
        transactions.add(notExpected);

        Transaction expected1 = new Transaction(800, new Date(2018, 9, 9));
        transactions.add(expected1);

        Transaction expected2 = new Transaction(500, new Date(2018, 8, 9));
        transactions.add(expected2);

        Transaction expected3 = new Transaction(500, new Date(2018, 8, 10));
        transactions.add(expected3);

        List<Transaction> matches = Transactions.dateSearch(transactions,
                new Date(2018, 8, 0),
                new Date(2018, 10, 0));

        assertTrue(matches.contains(expected1) && matches.contains(expected2)
                && matches.contains(expected3) && !matches.contains(notExpected));

    }

    @Test
    public void dateSorting() {

        ArrayList<Transaction> transactions = new ArrayList<>();

        Transaction fourth = new Transaction(500, new Date(2018, 10, 9));
        transactions.add(fourth);

        Transaction third = new Transaction(800, new Date(2018, 9, 9));
        transactions.add(third);

        Transaction first = new Transaction(500, new Date(2018, 8, 9));
        transactions.add(first);

        Transaction second = new Transaction(500, new Date(2018, 8, 10));
        transactions.add(second);

        List<Transaction> matches = Transactions.sortByDate(transactions);

        assertTrue(matches.get(0).equals(first)
                && matches.get(1).equals(second)
                && matches.get(2).equals(third)
                && matches.get(3).equals(fourth)
        );

    }
}
