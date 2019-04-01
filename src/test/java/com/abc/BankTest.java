package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.NoSuchElementException;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(AccountType.CHECKING);

        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill").openAccount(AccountType.CHECKING);
        bank.addCustomer(bill);

        bill.getAccounts().get(0).deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("Bill").openAccount(AccountType.SAVINGS));

        bank.getFirstCustomer().getAccounts().get(0).deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("Bill").openAccount(AccountType.MAXI_SAVINGS));

        bank.getFirstCustomer().getAccounts().get(0).deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test(expected = NoSuchElementException.class)
    public void checkErrorIfNoCustomer() {
        Bank bank = new Bank ();
        bank.getFirstCustomer();
        fail( "No exception was thrown though expected" );
    }

}
