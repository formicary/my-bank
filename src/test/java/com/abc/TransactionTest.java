package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void transactionManual() {
        Transaction t = new Transaction(5.0, Transaction.TransactionType.MANUAL);
        assertEquals("Transaction {amount=" + 5.0 + ",transactionType=MANUAL}", t.toString());
    }

    @Test
    public void transactionInterest() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.deposit(500.0);
        checkingAccount.addInterest();
        Transaction t = checkingAccount.getTransactions().get(1);
        assertEquals("Transaction {amount=" + (500.0 * (0.001 / 365)) + ",transactionType=INTEREST}",
                t.toString());
    }
}
