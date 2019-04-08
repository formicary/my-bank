package com.abc;

import org.junit.Test;
import static org.junit.Assert.*;


public class CustomerTest {
    @Test
    public void customer() {
        Customer john = new Customer(0,"John Smith");
        Account acc1 = new Account(0);
        Account acc2 = new Account(1);

        
        assertTrue(john instanceof Customer);
        assertEquals(john.getName(),"John Smith");
        assertEquals(john.getNumberOfAccounts(),0);
        assertEquals(john.totalInterestEarned(),0);

        john.openAccount(acc1);
        acc1.deposit(2000);
        acc1.withdraw(500);

        assertEquals(john.getNumberOfAccounts(),1);
        assertEquals(john.totalInterestEarned(),1.5);

        john.openAccount(acc2);
        john.transferToAccount(500, acc1, acc2);

        assertEquals(acc2.currentBalance(),500);
    }
}