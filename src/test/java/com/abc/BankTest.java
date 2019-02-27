package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private class DummyCustomer extends Customer {
        public DummyCustomer() { super(""); }
        public String getName() { return "Dummy Customer"; }
        public String getSummary() { return "Dummy Customer Summary"; }
        public double totalInterestEarned() { return 10.0; }
    }

    @Test
    public void testSummary() {
        Bank bank = new Bank()
            .addCustomer(new DummyCustomer());

        assertEquals("Customer Summary\n" +
                     " - Dummy Customer Summary", bank.customerSummary());
    }

    @Test
    public void testSummaryMultipleCustomers() {
        Bank bank = new Bank()
            .addCustomer(new DummyCustomer())
            .addCustomer(new DummyCustomer());

        assertEquals("Customer Summary\n" +
                     " - Dummy Customer Summary\n" +
                     " - Dummy Customer Summary", bank.customerSummary());
    }

    @Test
    public void testInterestPaid() {
        Bank bank = new Bank()
            .addCustomer(new DummyCustomer())
            .addCustomer(new DummyCustomer());

        assertEquals(20.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
