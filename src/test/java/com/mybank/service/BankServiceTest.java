package com.mybank.service;

import com.mybank.entity.Account;
import com.mybank.entity.AccountType;
import com.mybank.entity.Bank;
import com.mybank.entity.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankServiceTest {
    private static final double DOUBLE_DELTA = 1e-3;
    private CustomerService customerService = new CustomerService();
    private BankService bankService = new BankService();
    private AccountService accountService = new AccountService();

    @Test
    public void customerSummary() {

        Bank bank = new Bank();
        Customer john = new Customer("John");
        customerService.openAccount(john, new Account(AccountType.CHECKING));
        bankService.addCustomer(bank, john);

        assertEquals("Customer Summary\n - John (1 account)", bankService.customerSummary(bank));
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill");
        customerService.openAccount(bill, checkingAccount);
        bankService.addCustomer(bank, bill);

        accountService.deposit(checkingAccount, 100.0);

        assertEquals(0.1, bankService.totalInterestPaid(bank), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Customer bill = new Customer("Bill");
        customerService.openAccount(bill, savingsAccount);
        bankService.addCustomer(bank, bill);

        accountService.deposit(savingsAccount, 1500.0);

        assertEquals(2.0, bankService.totalInterestPaid(bank), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        customerService.openAccount(bill, maxiSavingsAccount);
        bankService.addCustomer(bank, bill);

        accountService.deposit(maxiSavingsAccount, 3000.0);

        assertEquals(150.00, bankService.totalInterestPaid(bank), DOUBLE_DELTA);
    }

    @Test
    public void interestPaidSummary() {

        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        customerService.openAccount(bill, maxiSavingsAccount);
        bankService.addCustomer(bank, bill);

        accountService.deposit(maxiSavingsAccount, 3000.0);

        assertEquals("Total interest paid: 150.00", bankService.totalInterestPaidReport(bank));
    }

    @Test
    public void getFirstCustomerNoCustomers() {

        Bank bank = new Bank();
        String first = bankService.getFirstCustomer(bank);
        assertEquals("No customers registered", first);
    }

    @Test
    public void getFirstCustomer() {

        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        customerService.openAccount(bill, maxiSavingsAccount);
        bankService.addCustomer(bank, bill);

        Customer john = new Customer("John");
        customerService.openAccount(john, new Account(AccountType.CHECKING));
        bankService.addCustomer(bank, john);

        String first = bankService.getFirstCustomer(bank);

        assertEquals("Bill", first);
    }
}

