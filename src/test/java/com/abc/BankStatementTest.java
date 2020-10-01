package com.abc;

import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.entity.impl.AccountImpl;
import com.abc.entity.impl.AccountType;
import com.abc.entity.impl.BankImpl;
import com.abc.entity.impl.CustomerImpl;
import com.abc.exception.InvalidBankException;
import com.abc.service.BankStatementService;
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
        customer1.addAccount(new AccountImpl(AccountType.CURRENT));
        bank.addCustomer(customer1);
        assertEquals("Bank report with one customer is not printer as expected",
                "Customer Summary\n" +
                        " - customer (1 account)",
                BankStatementService.bankCustomerReport(bank));
    }
    @Test
    public void oneCustomerTwoAccountsReport(){
        Customer customer1 = new CustomerImpl("customer");
        customer1.addAccount(new AccountImpl(AccountType.CURRENT));
        customer1.addAccount(new AccountImpl(AccountType.SAVINGS));
        bank.addCustomer(customer1);
        assertEquals("Bank report with one customer is not printer as expected",
                "Customer Summary\n" +
                        " - customer (2 accounts)",
                BankStatementService.bankCustomerReport(bank));
    }

    @Test
    public void oneCustomerThreeAccountsReport(){
        Customer customer1 = new CustomerImpl("customer");
        customer1.addAccount(new AccountImpl(AccountType.CURRENT));
        customer1.addAccount(new AccountImpl(AccountType.SAVINGS));
        customer1.addAccount(new AccountImpl(AccountType.MAXI_SAVINGS));
        bank.addCustomer(customer1);
        assertEquals("Bank report with one customer is not printer as expected",
                "Customer Summary\n" +
                        " - customer (3 accounts)",
                BankStatementService.bankCustomerReport(bank));
    }

    @Test
    public void multipleCustomersWithMultipleAccountsReport(){
        Customer customer1 = new CustomerImpl("customer");
        customer1.addAccount(new AccountImpl(AccountType.CURRENT));
        customer1.addAccount(new AccountImpl(AccountType.SAVINGS));
        customer1.addAccount(new AccountImpl(AccountType.MAXI_SAVINGS));
        Customer customer2 = new CustomerImpl("customer2");
        customer2.addAccount(new AccountImpl(AccountType.CURRENT));
        customer2.addAccount(new AccountImpl(AccountType.SAVINGS));
        Customer customer3 = new CustomerImpl("customer3");
        customer3.addAccount(new AccountImpl(AccountType.CURRENT));

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
