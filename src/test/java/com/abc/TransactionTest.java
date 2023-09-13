package com.abc;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void test_TransactionCreation() {
        Account a = new CheckingAccount();
        a.deposit(10);
        Optional<Transaction> t = a.getTransactions().stream().findAny();
        assertTrue(t.isPresent());
        assertEquals(1, a.getTransactions().size());
        assertEquals(TransactionType.DEPOSIT.getType(),t.get().getTransactionType().getType());
    }

}
