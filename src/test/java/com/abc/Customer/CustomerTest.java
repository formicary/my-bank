package com.abc.Customer;

import com.abc.Account.CheckingAccount;
import com.abc.Account.SavingsAccount;
import com.abc.Customer.Customer;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest {

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.addAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.addAccount(new SavingsAccount());
        oscar.addAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.addAccount(new SavingsAccount());
        oscar.addAccount(new CheckingAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
