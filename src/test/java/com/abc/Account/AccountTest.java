package com.abc.Account;

import com.abc.Exception.InsufficientBalanceException;
import com.abc.Transaction;
import org.junit.Test;

import com.abc.Money;;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AccountTest {

    @Test
    public void getTransactions() {
        Account account = new CheckingAccount();
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(new Transaction(new Money("10")));
        transactions.add(new Transaction(new Money("15")));
        transactions.add(new Transaction(new Money("-10")));
        transactions.add(new Transaction(new Money("-5")));
        try{
            for(Transaction t : transactions){
                account.processTransaction(t);
            }
        } catch(InsufficientBalanceException e) {
            fail("Unexpected Insufficient Balance Exception");
        }
        assertEquals(transactions, account.getTransactions());
    }

    @Test
    public void processTransaction() {
        Account account = new CheckingAccount();
        boolean pass = false;
        try { // Check exception is NOT called on deposit
            account.processTransaction(new Transaction(new Money("10")));
        } catch(InsufficientBalanceException e){
            fail("Unexpected Insufficient Balance Exception");
        }
        try { // Check exception is NOT called when balance is 0
            account.processTransaction(new Transaction(new Money("-10")));
        } catch(InsufficientBalanceException e){
            fail("Unexpected Insufficient Balance Exception");
        }
        try { // Check exception IS called when balance would be negative
            account.processTransaction(new Transaction(new Money("-1")));
        } catch(InsufficientBalanceException e){
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void getBalance() {
        Account account = new CheckingAccount();
        assertEquals(account.getBalance(), new Money("0"));
        try {
            account.processTransaction(new Transaction(new Money("10.50")));
        } catch(InsufficientBalanceException e){}
        assertEquals(account.getBalance(), new Money("10.50"));
        try {
            account.processTransaction(new Transaction(new Money("-1.30")));
        } catch(InsufficientBalanceException e){}
        assertEquals(account.getBalance(), new Money("9.20"));
    }
}