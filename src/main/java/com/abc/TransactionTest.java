package com.abc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class TransactionTest {


    @Test(expected = IllegalArgumentException.class)
    public void checktransactionType() {
        Transaction transaction = new Transaction("5135102", null, 50);

    }


    @Test(expected = IllegalArgumentException.class)
    public void checkTransactionIDNotNull() {
        Transaction transaction = new Transaction(null, Transaction.TranType.WITHDRAW,50);

    }

    @Test
    public void checkTransactionIsConstructedCorrectly() {
        Transaction transaction = new Transaction("5362323", Transaction.TranType.WITHDRAW,50);
        assertEquals("5362323",transaction.getTransactionId());
        assertEquals(Transaction.TranType.WITHDRAW,transaction.getTransactionType());
        assertEquals(50,transaction.getAmount());
    }


    @Test
    public void checkMultipleTransactions() {
        Transaction transaction1 = new Transaction("646", Transaction.TranType.WITHDRAW,-50);
        Transaction transaction2 = new Transaction("545", Transaction.TranType.INTEREST,50);
        Transaction transaction3 = new Transaction("65263", Transaction.TranType.DEPOSIT,50);
        List<Transaction> transactionList = Stream.of(transaction1,transaction2,transaction3).collect(Collectors.toList());
        double actual = transactionList.stream().mapToDouble(Transaction::getAmount).sum();
        assertEquals(50,actual);

    }

}



