package com.mybank.service;


import com.mybank.entity.Account;
import com.mybank.entity.AccountType;
import com.mybank.entity.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerServiceTest {

    private static final double DOUBLE_DELTA = 1e-3;

    private CustomerService customerService = new CustomerService();
    private AccountService accountService = new AccountService();

    @Test
    public void testCustomerStatementGeneration(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry");
        customerService.openAccount(henry, checkingAccount);
        customerService.openAccount(henry, savingsAccount);

        accountService.deposit(checkingAccount, 100.0);
        accountService.deposit(savingsAccount, 4000.0);
        accountService.withdraw(savingsAccount, 200.0);

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
                "Total In All Accounts $3,900.00", customerService.getStatement(henry));
    }

    @Test
    public void testOneAccount(){
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Customer oscar = new Customer("Oscar");
        customerService.openAccount(oscar, savingsAccount);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        customerService.openAccount(oscar, new Account(AccountType.SAVINGS));
        customerService.openAccount(oscar, new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        customerService.openAccount(oscar, new Account(AccountType.SAVINGS));
        customerService.openAccount(oscar, new Account(AccountType.CHECKING));
        customerService.openAccount(oscar, new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransfer() {
        Customer oscar = new Customer("Oscar");
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account checkingsAccount = new Account(AccountType.CHECKING);
        customerService.openAccount(oscar, savingsAccount);
        customerService.openAccount(oscar, checkingsAccount);

        accountService.deposit(savingsAccount, 1000.0);
        accountService.deposit(checkingsAccount, 2000.0);

        accountService.transfer(savingsAccount, checkingsAccount,  500.0);

        assertEquals(500.0, accountService.sumTransactions(savingsAccount), DOUBLE_DELTA);
        assertEquals(2500.0, accountService.sumTransactions(checkingsAccount), DOUBLE_DELTA);
    }
}

