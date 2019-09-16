package com.abc.Customer;

import com.abc.Account.CheckingAccount;
import com.abc.Account.MaxiSavingsAccount;
import com.abc.Account.SavingsAccount;
import com.abc.Customer.Customer;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest {

    @Test
    public void getNumberOfAccounts(){
        Customer oscar = new Customer("Oscar");
        oscar.addAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
        oscar.addAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
        oscar.addAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void getAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.addAccount(new SavingsAccount());
        oscar.addAccount(new CheckingAccount());
        oscar.addAccount(new MaxiSavingsAccount());
        assertEquals("Savings Account", oscar.getAccounts().get(0).getName());
        assertEquals("Checking Account", oscar.getAccounts().get(1).getName());
        assertEquals("Maxi-Savings Account", oscar.getAccounts().get(2).getName());
    }

    public void getName() {
        Customer oscar = new Customer("oscar");
        assertEquals("oscar", oscar.getName());
    }
}
