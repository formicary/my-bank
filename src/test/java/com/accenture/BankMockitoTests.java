package com.accenture;

import com.accenture.accounts.Account;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class BankMockitoTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void bankCanApplyDailyInterest() {
        Bank bank = new Bank();

        Customer customer = mock(Customer.class);
        Account account = mock(Account.class);

        bank.addCustomer(customer);

        ArrayList<Account> accountList = new ArrayList<>();
        accountList.add(account);

        when(customer.getAccounts()).thenReturn(accountList);

        bank.applyDailyInterest();

        verify(account, times(1)).applyDailyInterest();
    }


    @Test
    public void callsCustomersForTotalInterestPaidAndSumsCorrectly() {
        Bank bank = new Bank();

        Customer customer = mock(Customer.class);
        Customer customer2 = mock(Customer.class);

        when(customer.getTotalInterestPaidToCustomer()).thenReturn(DollarAmount.fromInt(10));
        when(customer2.getTotalInterestPaidToCustomer()).thenReturn(DollarAmount.fromInt(32));
        when(customer.getId()).thenReturn("test1");
        when(customer2.getId()).thenReturn("test2");

        bank.addCustomer(customer);
        bank.addCustomer(customer2);

        DollarAmount totalInterestPaid = bank.totalInterestPaid();

        verify(customer, times(1)).getTotalInterestPaidToCustomer();
        verify(customer2, times(1)).getTotalInterestPaidToCustomer();


        assertEquals(DollarAmount.fromInt(42), totalInterestPaid);
    }

}
