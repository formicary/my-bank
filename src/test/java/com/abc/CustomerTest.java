package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void TestOpenAccount_OneAccount_ShouldReturnOne() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));

        assertEquals(1, oscar.getNumberOfAccounts());
    }
}
