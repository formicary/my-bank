package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionTest {
    @Test //Test Constructor with Positive Amount
    public void transaction_PositiveAmount_CreateNewTransaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
    @Test //Test Constructor with Negative Amount
    public void transaction_NegativeAmount_CreateNewTransaction() {
        Transaction t = new Transaction(-5);
        assertTrue(t instanceof Transaction);
    }
    
    @Test //Test Constructor with Zero Amount
    public void transaction_ZeroAmount_CreateNewTransaction() {
        Transaction t = new Transaction(0);
        assertTrue(t instanceof Transaction);
    }
    
    @Test //Test Constructor with a Given Date
    public void transaction_GivenDate_CreateNewTransaction() {
    	LocalDate date = LocalDate.of(2018,05,05);
        Transaction t = new Transaction(50, date.atStartOfDay());
        assertTrue(t instanceof Transaction);
    }
    
    @Test //Test getAmount function for transaction with positive amount
    public void getAmount_PosAmount_ReturnAmount() {
    	Transaction t = new Transaction(100);
        assertEquals(100.00, t.getAmount(), 0.00);
    }
    
    @Test //Test getAmount function for transaction with negative amount
    public void getAmount_NegAmount_ReturnAmount() {
    	Transaction t = new Transaction(-100);
        assertEquals(-100.00, t.getAmount(), 0.00);
    }
    
    @Test //Test getAmount function for transaction with negative amount
    public void getAmount_ZeroAmount_ReturnAmount() {
    	Transaction t = new Transaction(0);
        assertEquals(0.00, t.getAmount(), 0.00);
    }
    
    
    @Test //Test Constructor without a Given Date
    public void getDate_CurrentDateTime_ReturnDate() {
    	LocalDateTime date = LocalDateTime.now();
        Transaction t = new Transaction(50);
        assertEquals(date, t.getDateTime());
    }
    
    @Test //Test Constructor with a Given Date
    public void getDate_GivenDate_ReturnDate() {
    	LocalDate date = LocalDate.of(2018,05,05);
        Transaction t = new Transaction(50, date.atStartOfDay());
        assertEquals(date.atStartOfDay(), t.getDateTime());
    }
}
