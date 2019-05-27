package com.accenture;

import com.accenture.accounts.Account;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerMockitoTests {

    @Mock
    Account account1;

    @Mock
    Account account2;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void sumsTotalInterestPaidCorrectly() {

        when(account1.getTotalInterestPaid()).thenReturn(DollarAmount.fromInt(10));
        when(account2.getTotalInterestPaid()).thenReturn(DollarAmount.fromInt(30));

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        Customer customer = new Customer.Builder().setForename("ab").setSurname("Testerson").setAge(20).setAccounts(accounts).build();

        assertEquals(DollarAmount.fromInt(40), customer.getTotalInterestPaidToCustomer());
    }
}
