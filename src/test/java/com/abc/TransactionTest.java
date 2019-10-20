package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransactionTest {
   
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void testGetTransactionDate() {
        Transaction t = new Transaction(100);
        assertNotNull(t.getTransactionDate());
    }
    
    @Test
    public void testGetDateDifference() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = formatter.parse("10-10-2019");  
        Date date2 = formatter.parse("20-10-2019");
        
        Transaction t = new Transaction(5, date2);
        
        assertEquals(10, DateProvider.getDateDiff(date, t.getTransactionDate()));
    }
}
