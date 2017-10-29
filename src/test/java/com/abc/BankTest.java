package com.abc;

import java.math.BigDecimal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(Account.AccountType.CHECKING);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        //Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill");
        bill.openAccount(Account.AccountType.CHECKING);
        bank.addCustomer(bill);

        bill.getAccount(Account.AccountType.CHECKING).deposit(BigDecimal.valueOf(100));
        
        BigDecimal d = BigDecimal.valueOf(0.1);
        assertTrue(d.compareTo(bank.totalInterestPaid()) == 0);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.openAccount(Account.AccountType.SAVINGS);
        bank.addCustomer(bill);

        bill.getAccount(Account.AccountType.SAVINGS).deposit(BigDecimal.valueOf(1500));

        assertTrue(BigDecimal.valueOf(2.0).compareTo(bank.totalInterestPaid()) == 0);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.openAccount(Account.AccountType.MAXI_SAVINGS);
        bank.addCustomer(bill);

        bill.getAccount(Account.AccountType.MAXI_SAVINGS).deposit(BigDecimal.valueOf(3000));

        assertTrue(BigDecimal.valueOf(170).compareTo(bank.totalInterestPaid()) == 0);
        
    }

}
