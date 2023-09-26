package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Bank;
import com.abc.accenture.financial.items.Customer;
import com.abc.accenture.financial.items.account.AccountGenerator;
import org.junit.Test;

import java.time.LocalDate;

import static com.abc.accenture.financial.items.account.AccountType.MAXI_SAVINGS;
import static com.abc.accenture.financial.items.account.AccountType.SAVINGS;
import static org.junit.Assert.assertEquals;

public class BankServiceTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private final AccountService accountService = new AccountServiceImpl(new AccountGenerator());

    private final CustomerService customerService = new CustomerServiceImpl(accountService);

    private final BankService bankService = new BankServiceImpl(customerService);

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();

        bankService.openAccount(bank, "1", "John");

        assertEquals("Customer Summary\n - John (1 account)", bankService.customerSummary(bank));
    }

    @Test
    public void testCheckingAccount() {
        Bank bank = new Bank();
        String customerName = "Bill";
        bankService.openAccount(bank, "1", customerName);

        bankService.deposit(bank, customerName, "1", 100.0);

        assertEquals(0.1, bankService.totalInterestPaid(bank), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccount() {
        Bank bank = new Bank();
        String customerName = "Bill";
        bankService.openAccount(bank, customerName, "1", SAVINGS);

        bankService.deposit(bank, customerName, "1", 1500.0);

        assertEquals(2.0, bankService.totalInterestPaid(bank), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccount() {
        Bank bank = new Bank();
        String customerName = "Bill";
        Customer currentCustomer = bankService.openAccount(bank, customerName, "1", MAXI_SAVINGS)
                .getCurrentCustomer();

        bankService.deposit(bank, customerName, "1", 3000.0);

        assertEquals(15.0, bankService.totalInterestPaid(bank), DOUBLE_DELTA);
        assertEquals(currentCustomer.getName(), currentCustomer.getName());
    }

    @Test
    public void testMaxiSavingsAccountPast10Day() {
        Bank bank = new Bank();
        String customerName = "Bill";
        bankService.openAccount(bank, customerName, "1", MAXI_SAVINGS);

        bankService.deposit(bank, customerName, "1", 3000.0,
                LocalDate.now().minusDays(3));
        bankService.deposit(bank, customerName, "1", 3000.0,
                LocalDate.now().minusDays(10));
        bankService.deposit(bank, customerName, "1", 3000.0);
        bankService.deposit(bank, customerName, "1", 3000.0);
        bankService.deposit(bank, customerName, "1", 3000.0);

        assertEquals(75.0, bankService.totalInterestPaid(bank), DOUBLE_DELTA);

        bankService.withdraw(bank, customerName, "1", 3000.0,
                LocalDate.now().minusDays(8));

        assertEquals(60.0, bankService.totalInterestPaid(bank), DOUBLE_DELTA);
    }
}
