package com.abc.customer;

import com.abc.Money;
import com.abc.Transaction;
import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.SavingsAccount;
import com.abc.exception.InsufficientBalanceException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransferTest {

    @Test
    public void testValidTransfer() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        try {
            savingsAccount.processTransaction(new Transaction(new Money("300")));
        } catch (InsufficientBalanceException e) {
            fail("Unexpected Inssufficent Balance Exception");
        }
        try {
            new Transfer(savingsAccount, checkingAccount, new Money("300")).processTransfer();
        } catch (InsufficientBalanceException e) {
            fail("Unexpected Inssufficent Balance Exception");
        }
        assertEquals(new Money("300"), checkingAccount.getBalance());
        assertEquals(new Money("0"), savingsAccount.getBalance());
    }

    @Test
    public void testInvalidTransfer() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        boolean pass = false;

        try {
            savingsAccount.processTransaction(new Transaction(new Money("300")));
        } catch (InsufficientBalanceException e) {
            fail("Unexpected Inssufficent Balance Exception");
        }
        try {
            new Transfer(savingsAccount, checkingAccount, new Money("350")).processTransfer();
        } catch (InsufficientBalanceException e) {
            pass = true;
        }
        assert(pass);
    }
}