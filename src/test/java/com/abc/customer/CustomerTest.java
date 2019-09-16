package com.abc.customer;

import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;
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
        assertEquals("Savings account", oscar.getAccounts().get(0).getName());
        assertEquals("Checking account", oscar.getAccounts().get(1).getName());
        assertEquals("Maxi-Savings account", oscar.getAccounts().get(2).getName());
    }

    public void getName() {
        Customer oscar = new Customer("oscar");
        assertEquals("oscar", oscar.getName());
    }
}
