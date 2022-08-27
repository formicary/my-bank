package com.abc.customer;

import com.abc.account.*;
import com.abc.bank.Bank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerStatementTest {

    @Mock
    private Customer mockCustomer;

    @Mock
    private SavingsAccount mockSavingsAccount;

    @Mock
    private CheckingAccount mockCheckingAccount;

    @Test
    public void create() {
        Transaction transaction1 = new Transaction(Transaction.TransactionType.DEPOSIT, 100.0);
        Transaction transaction2 = new Transaction(Transaction.TransactionType.DEPOSIT, 4000.0);
        Transaction transaction3 = new Transaction(Transaction.TransactionType.WITHDREW, 200.0);

        when(mockCustomer.getName()).thenReturn("Henry");
        when(mockCustomer.getAccounts()).thenReturn(new ArrayList<>(Arrays.asList(mockCheckingAccount, mockSavingsAccount)));
        when(mockCheckingAccount.getTransactions()).thenReturn(new ArrayList<>(Arrays.asList(transaction1)));
        when(mockSavingsAccount.getTransactions()).thenReturn(new ArrayList<>(Arrays.asList(transaction2, transaction3)));
        when(mockCheckingAccount.getAccountType()).thenReturn(AccountType.CHECKING);
        when(mockSavingsAccount.getAccountType()).thenReturn(AccountType.SAVINGS);
        when(mockCheckingAccount.sumTransactions()).thenReturn(100.0);
        when(mockSavingsAccount.sumTransactions()).thenReturn(3800.0);
        when(mockCustomer.getStatement()).thenCallRealMethod();

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", mockCustomer.getStatement());
    }

}