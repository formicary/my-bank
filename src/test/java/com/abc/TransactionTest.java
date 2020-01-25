package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionTest {
    @Test
    public void testTransaction() {
        Transaction transaction = new Transaction(5);
        assertTrue(transaction instanceof Transaction);
    }
    
    //test the checking of transaction dates
    @Test
    public void testTransactionIsOverTenDays() {
    	 Transaction transaction = new Transaction(5);
    	 Calendar calender=Calendar.getInstance();
    	 calender.add(Calendar.DATE, 10);
    	 Date dateAdd10=calender.getTime();
    	 
    	 assert(transaction.transactionIsOverTenDaysOld(dateAdd10));
    	 
    }
    
    //test the checking of transaction dates
    @Test
    public void testTransactionIsUnderTenDays() {
    	 Transaction transaction = new Transaction(5);
    	 Calendar calender=Calendar.getInstance();
    	 calender.add(Calendar.DATE, 9);
    	 Date dateAdd10=calender.getTime();
    	 
    	 assert(!transaction.transactionIsOverTenDaysOld(dateAdd10));
    	 
    }
}
