package com.abc.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    private final Bank uut = new Bank();

    @Test
    public void totalInterestPaid() {
        // given
        final MockCustomer customer1 = new MockCustomer("Joe");
        final MockCustomer customer2 = new MockCustomer("Jane");
        customer1.interest = 10.0d;
        customer2.interest = 5.0d;
        uut.addCustomer(customer1);
        uut.addCustomer(customer2);
        // when
        final double interest = uut.totalInterestPaid();
        // then
        assertEquals(15.0d, interest, DOUBLE_DELTA);
    }

    @Test
    public void summary() {
        // given
        final MockCustomer customer1 = new MockCustomer("Joe");
        final MockCustomer customer2 = new MockCustomer("Jane");
        customer1.shortDescription = "John Doe (1 account)";
        customer2.shortDescription = "Jane Doe (3 accounts)";
        uut.addCustomer(customer1);
        uut.addCustomer(customer2);
        // when
        final String summary = uut.customersSummary();
        // then
        assertEquals("Customer Summary\n" +
                        " - John Doe (1 account)\n" +
                        " - Jane Doe (3 accounts)",
                summary);
    }

}
