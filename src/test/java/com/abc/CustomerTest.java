package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private class DummyAccount extends Account {
        public double interestEarned() { return 0.01; }
        public String accountType() { return "Dummy Account"; }

        public double getBalance() { return 10.0; }
        public String getStatement() { return "Dummy Account Statement\n"; }
    }

    @Test
    public void testGetStatement() {
        Customer henry = new Customer("Henry")
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount());

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Dummy Account Statement\n" +
                "\n" +
                "Dummy Account Statement\n" +
                "\n" +
                "Total In All Accounts $20.00", henry.getStatement());
    }

    @Test
    public void testInterestEarned() {
        Customer james = new Customer("James")
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount());

        assertEquals(0.02, james.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar")
            .openAccount(new DummyAccount());

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount());

        assertEquals(2, oscar.getNumberOfAccounts());
    }
}
