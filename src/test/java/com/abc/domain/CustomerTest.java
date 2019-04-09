package com.abc.domain;

import com.abc.exceptions.InsufficientFundsException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;
    private static final String NAME = "John Doe";

    private final Customer uut = new Customer(NAME);

    @Test
    public void testName() {
        assertEquals(NAME, uut.getName());
    }

    @Test
    public void accountNumber_0() {
        assertEquals(0, uut.getNumberOfAccounts());
    }

    @Test
    public void accountNumber_2() {
        uut.openAccount(new Account(new MockAccountType()));
        uut.openAccount(new Account(new MockAccountType()));
        assertEquals(2, uut.getNumberOfAccounts());
    }

    @Test
    public void totalInterest() {
        // given
        final MockAccount account1 = new MockAccount();
        final MockAccount account2 = new MockAccount();
        account1.interest = 10.0d;
        account2.interest = 3.0d;
        uut.openAccount(account1);
        uut.openAccount(account2);
        // when
        final double interest = uut.totalInterestEarned();
        // then
        assertEquals(13.0d, interest, DOUBLE_DELTA);
    }

    @Test
    public void shortDescription() {
        // given
        uut.openAccount(new MockAccount());
        uut.openAccount(new MockAccount());
        // when
        final String shortDescription = uut.getShortDescription();
        // then
        assertEquals("John Doe (2 accounts)", shortDescription);
    }

    @Test
    public void transfer() throws InsufficientFundsException {
        // given
        final MockAccount sourceAccount = new MockAccount();
        final MockAccount targetAccount = new MockAccount();
        sourceAccount.balance = 10.0d;
        targetAccount.balance = 3.0d;
        uut.openAccount(sourceAccount);
        uut.openAccount(targetAccount);
        // when
        uut.transfer(sourceAccount, targetAccount, 5.0d);
        // then
        final List<Transaction> sourceTransactions = sourceAccount.getTransactions();
        final List<Transaction> targetTransactions = targetAccount.getTransactions();
        assertEquals(-5.0d, sourceTransactions.get(0).getAmount(), DOUBLE_DELTA);
        assertEquals(5.0d, targetTransactions.get(0).getAmount(), DOUBLE_DELTA);
    }

    @Test(expected = InsufficientFundsException.class)
    public void transfer_NoFunds() throws InsufficientFundsException {
        // given
        final MockAccount sourceAccount = new MockAccount();
        final MockAccount targetAccount = new MockAccount();
        sourceAccount.balance = 10.0d;
        targetAccount.balance = 3.0d;
        uut.openAccount(sourceAccount);
        uut.openAccount(targetAccount);
        // when
        uut.transfer(sourceAccount, targetAccount, 1000.0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfer_invalidSourceAccount() throws InsufficientFundsException {
        // given
        final MockAccount sourceAccount = new MockAccount();
        final MockAccount targetAccount = new MockAccount();
        sourceAccount.balance = 10.0d;
        targetAccount.balance = 3.0d;
        uut.openAccount(targetAccount);
        // when
        uut.transfer(sourceAccount, targetAccount, 1.0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfer_invalidTargetAccount() throws InsufficientFundsException {
        // given
        final MockAccount sourceAccount = new MockAccount();
        final MockAccount targetAccount = new MockAccount();
        sourceAccount.balance = 10.0d;
        targetAccount.balance = 3.0d;
        uut.openAccount(sourceAccount);
        // when
        uut.transfer(sourceAccount, targetAccount, 1.0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfer_nullSourceAccount() throws InsufficientFundsException {
        // given
        final MockAccount sourceAccount = new MockAccount();
        final MockAccount targetAccount = new MockAccount();
        sourceAccount.balance = 10.0d;
        targetAccount.balance = 3.0d;
        uut.openAccount(sourceAccount);
        uut.openAccount(targetAccount);
        // when
        uut.transfer(null, targetAccount, 1.0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfer_nullTargetAccount() throws InsufficientFundsException {
        // given
        final MockAccount sourceAccount = new MockAccount();
        final MockAccount targetAccount = new MockAccount();
        sourceAccount.balance = 10.0d;
        targetAccount.balance = 3.0d;
        uut.openAccount(sourceAccount);
        uut.openAccount(targetAccount);
        // when
        uut.transfer(sourceAccount, null, 1.0d);
    }

}
