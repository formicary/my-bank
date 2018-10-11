package com.abc;

import com.abc.customer.Account;
import com.abc.bank.Bank;
import com.abc.customer.AccountType;
import com.abc.customer.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.getCustomers().add(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.getCustomers().add(bill);

        checkingAccount.addTransaction(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.SAVINGS);
        bank.getCustomers().add(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.addTransaction(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.getCustomers().add(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.addTransaction(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
