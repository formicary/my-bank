package com.abc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankTest {

    private final double DELTA = 1e-15;

    @Mock
    Customer CUSTOMER_1;
    @Mock
    Customer CUSTOMER_2;
    @Mock
    Customer CUSTOMER_3;

    @Test
    public void testCustomerSummary() {
        when(CUSTOMER_1.getName()).thenReturn("John");
        when(CUSTOMER_1.getNumberOfAccounts()).thenReturn(1);
        when(CUSTOMER_2.getName()).thenReturn("James");
        when(CUSTOMER_2.getNumberOfAccounts()).thenReturn(2);

        Bank bank = new Bank();
        bank.addCustomer(CUSTOMER_1);
        bank.addCustomer(CUSTOMER_2);

        assertEquals(
                "Customer Summary\n - John (1 account)\n - James (2 accounts)",
                bank.createCustomerSummary());
    }

    @Test
    public void testAddCustomer() {
        Bank bank = new Bank();
        bank.addCustomer(CUSTOMER_1);
        bank.addCustomer(CUSTOMER_2);
        bank.addCustomer(CUSTOMER_3);
        bank.addCustomer(CUSTOMER_1);
        bank.addCustomer(null);
        assertEquals(3, bank.getCustomers().size());
    }

    @Test
    public void testTotalInterestPaid() {
        when(CUSTOMER_1.totalInterestEarned()).thenReturn(1.0);
        when(CUSTOMER_2.totalInterestEarned()).thenReturn(2.0);
        when(CUSTOMER_3.totalInterestEarned()).thenReturn(3.0);
        Bank bank = new Bank();
        bank.addCustomer(CUSTOMER_1);
        bank.addCustomer(CUSTOMER_2);
        bank.addCustomer(CUSTOMER_3);
        assertEquals(6.0, bank.totalInterestPaid(), DELTA);
    }

    @Test
    public void testCreateAccount() {
        Bank bank = new Bank();
        assertNotNull(bank.createAccount(CUSTOMER_1, AccountType.CHECKING));
    }
}
