package com.abc;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankShould {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void GenerateCorrectUserSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void CalculateCorrectInterest() {
        Bank bank = new Bank();

        Account mockAccount = mock(Account.class);
        when(mockAccount.interestEarned()).thenReturn(0.1);

        Customer bill = new Customer("Bill").openAccount(mockAccount);
        bank.addCustomer(bill);



        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }



}
