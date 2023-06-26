package com.abc;

import com.abc.ENUMS.AccountType;
import com.abc.ENUMS.TransactionType;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    Customer oscar;
    Account checkingAccount;
    Account savingAccount;
    Account maxiSavingAccount;
    Bank bank;
    @Before
    public void init()
    {
        oscar=new Customer("Oscar");
        checkingAccount=new Account(AccountType.CHECKING);
        savingAccount=new Account(AccountType.SAVINGS);
        maxiSavingAccount=new Account(AccountType.MAXI_SAVINGS);
        bank=new Bank();

    }
    @Test
    public void customerSummaryTest() {
        oscar.openAccount(checkingAccount);
        bank.addCustomer(oscar);
        assertEquals("Customer Summary\n - Oscar (1 account(s))", bank.customerSummary());
    }


    @Test
    public void totalInterestPaidOnAllAccountsTest() {
        bank.addCustomer(oscar.openAccount(checkingAccount));
        bank.addCustomer(new Customer("will").openAccount(savingAccount));
        bank.addCustomer(new Customer("garry").openAccount(maxiSavingAccount));
        checkingAccount.deposit(3000.0,TransactionType.DEPOSIT);
        savingAccount.deposit(1000.0,TransactionType.DEPOSIT);
        maxiSavingAccount.deposit(10000.0,TransactionType.DEPOSIT);
        assertEquals(504.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
