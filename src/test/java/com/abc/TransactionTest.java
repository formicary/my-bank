package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.abc.Transaction.TransactionType;

public class TransactionTest {
	
	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test
    public void checkType() {
        Transaction t = new Transaction(5, TransactionType.DEPOSIT);
        assertEquals(TransactionType.DEPOSIT, t.getType());
    }
    
    @Test
    public void checkAmount() {
        Transaction t = new Transaction(300, TransactionType.DEPOSIT);
        assertEquals(300, t.getAmount(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkNotTransferSender() {
        Transaction t = new Transaction(300, TransactionType.WITHDRAW);
        assertEquals("This transaction was not a transfer", t.getSender());
    }
    
    @Test
    public void checkNotTransferReceiver() {
        Transaction t = new Transaction(300, TransactionType.WITHDRAW);
        assertEquals("This transaction was not a transfer", t.getReceiver());
    }
    
    @Test
    public void checkTransferSender() {
    	Account checkingAccount = new CheckingAccount();
    	Account savingsAccount = new SavingsAccount();
        Transaction t = new Transaction(300, TransactionType.TRANSFER, checkingAccount, savingsAccount);
        assertEquals("CHECKING", t.getSender());
    }
    
    @Test
    public void checkTransferReceiver() {
    	Account checkingAccount = new CheckingAccount();
    	Account savingsAccount = new SavingsAccount();
        Transaction t = new Transaction(300, TransactionType.TRANSFER, checkingAccount, savingsAccount);
        assertEquals("SAVINGS", t.getReceiver());
    }
    
    @Test
    public void checkStatement() {
    	Account checkingAccount = new CheckingAccount();
    	Account savingsAccount = new SavingsAccount();
        Transaction t = new Transaction(300, TransactionType.TRANSFER, checkingAccount, savingsAccount);
        assertEquals("TRANSFER: 300.0 to: SAVINGS", t.getStatement());
    }
}
