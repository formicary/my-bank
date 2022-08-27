package com.abc.customer;

import com.abc.account.AccountFactory;
import com.abc.bank.Bank;
import com.abc.account.AccountType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest {

    @Mock
    private Bank mockBank;

    @Test
    public void testGetNumberOfAccountsWith0Accounts() {
        Customer oscar = new Customer("Oscar");
        assertEquals(0, oscar.getNumberOfAccounts());
    }

    @Test
    public void testGetNumberOfAccountsWith3Accounts() {
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar");
        bank.addCustomer(oscar);
        for (int i = 0; i < 3; i++) {
            oscar.openAccount(AccountType.SAVINGS);
        }
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenAccountWhenBankIsNull() {
        Customer customer = new Customer("Anna");
        customer.openAccount(AccountType.CHECKING);
    }

    @Test
    public void testOpenAccount() {
        Customer customer = new Customer("Anna");
        customer.setBank(mockBank);
        when(mockBank.createAccount(customer, AccountType.CHECKING))
                .thenReturn(AccountFactory.create(customer,AccountType.CHECKING));
        assertNotNull(customer.openAccount(AccountType.CHECKING));
    }
}
