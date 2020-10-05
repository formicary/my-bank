package com.abc.service;

import com.abc.entity.Account;
import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.entity.impl.AccountImpl;
import com.abc.entity.AccountType;
import com.abc.entity.impl.BankImpl;
import com.abc.entity.impl.CustomerImpl;
import com.abc.exception.InvalidBankException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankStatementTest {

    private static Bank bank;

    @Before
    public void setup(){
        bank = new BankImpl();
    }

    @Test(expected = InvalidBankException.class)
    public void nullBankThrowsException(){
        BankStatementService.bankCustomerReport(null);
    }

    @Test
    public void zeroCustomersShowEmptyReport(){
        assertEquals("Bank report with zero customers is not printing as expected", "Customer Summary", BankStatementService.bankCustomerReport(bank));
    }

    @Test
    public void oneCustomerZeroAccountsReport(){
        Customer customer1 = new CustomerImpl("customer");
        bank.addCustomer(customer1);
        assertEquals("Bank report with one customer is not printer as expected",
                "Customer Summary\n" +
                        " - customer (0 accounts)",
                BankStatementService.bankCustomerReport(bank));
    }

    @Test
    public void oneCustomerOneAccountReport(){
        Customer customer1 = new CustomerImpl("customer");
        Account account = new AccountImpl(customer1, AccountType.CURRENT, "123");
        bank.addCustomer(customer1);
        assertEquals("Bank report with one customer is not printer as expected",
                "Customer Summary\n" +
                        " - customer (1 account)",
                BankStatementService.bankCustomerReport(bank));
    }
    @Test
    public void oneCustomerTwoAccountsReport(){
        Customer customer1 = new CustomerImpl("customer");
        Account account = new AccountImpl(customer1, AccountType.CURRENT, "123");
        Account account1 = new AccountImpl(customer1, AccountType.SAVINGS, "124");

        bank.addCustomer(customer1);
        assertEquals("Bank report with one customer is not printer as expected",
                "Customer Summary\n" +
                        " - customer (2 accounts)",
                BankStatementService.bankCustomerReport(bank));
    }

    @Test
    public void oneCustomerThreeAccountsReport(){
        Customer customer1 = new CustomerImpl("customer");
        Account account = new AccountImpl(customer1, AccountType.CURRENT, "123");
        Account account1 = new AccountImpl(customer1, AccountType.SAVINGS, "124");
        Account account2 = new AccountImpl(customer1, AccountType.MAXI_SAVINGS, "125");
        bank.addCustomer(customer1);
        assertEquals("Bank report with one customer is not printer as expected",
                "Customer Summary\n" +
                        " - customer (3 accounts)",
                BankStatementService.bankCustomerReport(bank));
    }

    @Test
    public void multipleCustomersWithMultipleAccountsReport(){
        Customer customer1 = new CustomerImpl("customer");
        Account account = new AccountImpl(customer1, AccountType.CURRENT, "123");
        Account account1 = new AccountImpl(customer1, AccountType.SAVINGS, "124");
        Account account2 = new AccountImpl(customer1, AccountType.MAXI_SAVINGS, "125");
        Customer customer2 = new CustomerImpl("customer2");
        Account account3 = new AccountImpl(customer2, AccountType.CURRENT, "126");
        Account account4 = new AccountImpl(customer2, AccountType.SAVINGS, "127");
        Customer customer3 = new CustomerImpl("customer3");
        Account account5 = new AccountImpl(customer3, AccountType.CURRENT, "128");

        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        bank.addCustomer(customer3);
        assertEquals("Bank report with one customer is not printer as expected",
                "Customer Summary\n" +
                        " - customer (3 accounts)\n"+
                        " - customer2 (2 accounts)\n"+
                        " - customer3 (1 account)",
                BankStatementService.bankCustomerReport(bank));
    }
}
