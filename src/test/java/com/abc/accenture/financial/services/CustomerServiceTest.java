package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Customer;
import com.abc.accenture.financial.items.account.Account;
import com.abc.accenture.financial.items.account.AccountGenerator;
import org.junit.Test;

import static com.abc.accenture.financial.items.account.AccountType.*;
import static org.junit.Assert.assertEquals;

public class CustomerServiceTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private final AccountService accountService = new AccountServiceImpl(new AccountGenerator());

    private final CustomerService customerService = new CustomerServiceImpl(accountService);

    @Test
    public void testGetStatement() {
        Customer henry = customerService.openAccount(new Customer("Henry"), "1", CHECKING)
                .openAccount("2", SAVINGS).getCurrentCustomer();

        customerService.deposit(henry, "1", 100.0);
        customerService.deposit(henry, "2", 4000.0);
        customerService.withdraw(henry, "2", 200.0);

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
    public void testOneAccount() {
        Customer oscar = customerService.openAccount(new Customer("Oscar"), "1",
                SAVINGS).getCurrentCustomer();
        assertEquals(1, customerService.getNumberOfAccounts(oscar));
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = customerService.openAccount(new Customer("Oscar"), "1", SAVINGS)
                .openAccount("2", CHECKING)
                .getCurrentCustomer();

        assertEquals(2, customerService.getNumberOfAccounts(oscar));
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = customerService
                .openAccount(new Customer("Oscar"), "1", SAVINGS)
                .openAccount("2", CHECKING)
                .openAccount("3", MAXI_SAVINGS)
                .getCurrentCustomer();

        assertEquals(3, customerService.getNumberOfAccounts(oscar));
    }

    @Test
    public void testTransferBetweenAccount() {
        double baseAmount = 5000.0;
        double transferAmount = 2000.0;
        double diffAmount = 3000.0;
        Customer oscar = customerService
                .openAccount(new Customer("Oscar"), "1", SAVINGS)
                .openAccount("2", CHECKING)
                .getCurrentCustomer();

        customerService.deposit(oscar, "1", baseAmount);

        Account fromAccount = oscar.getAccounts().get("1");
        Account toAccount = oscar.getAccounts().get("2");

        assertEquals(baseAmount, accountService.sumTransactions(fromAccount.getTransactions()), DOUBLE_DELTA);
        assertEquals(0, accountService.sumTransactions(toAccount.getTransactions()), DOUBLE_DELTA);

        customerService.transferBetweenAccount(oscar, "1", "2", transferAmount);

        assertEquals(diffAmount, accountService.sumTransactions(fromAccount.getTransactions()), DOUBLE_DELTA);
        assertEquals(transferAmount, accountService.sumTransactions(toAccount.getTransactions()), DOUBLE_DELTA);
    }
}
