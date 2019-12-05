package com.abc;

import org.junit.Test;
import com.abc.Transaction.Type;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;

public class TransactionTest {
    @Test
    public void transactionTime() {
        Transaction t = new Transaction(5, Type.DEPOSIT);
        assertTrue(t.getDate().equals(LocalDate.now()));

        DateProvider.getInstance().nextDay();
        Transaction nextt = new Transaction(6, Type.DEPOSIT);
        assertTrue(nextt.getDate().equals(LocalDate.now().plusDays(1)));
    }
    
    @Test
    public void transactionString() {
        Transaction wTransaction = new Transaction(-5, Type.WITHDRAW);
        Transaction dTransaction = new Transaction(6, Type.DEPOSIT);
        Transaction iTransaction = new Transaction(1, Type.INTERESTS);
        Transaction t1Transaction = new Transaction(-8, "account 2");
        Transaction t2Transaction = new Transaction(8, "account 1");
        
        String currentDate = DateProvider.getInstance().now().toString();
        
        assertTrue(wTransaction.toString().equals(currentDate + ": withdrawal $-5.00"));
        assertTrue(dTransaction.toString().equals(currentDate + ": deposit $6.00"));
        assertTrue(iTransaction.toString().equals(currentDate + ": interests $1.00"));
        assertTrue(t1Transaction.toString().equals(currentDate + ": transfer $-8.00 (To account 2)"));
        assertTrue(t2Transaction.toString().equals(currentDate + ": transfer $8.00 (From account 1)"));
    }
    
}
