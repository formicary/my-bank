package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.Checking;
import com.abc.accounts.MaxiSavings;
import com.abc.accounts.Savings;
import com.abc.users.Customer;
import com.abc.users.Manager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Manager manager = new Manager("Bob", bank);
        Customer john = new Customer("John");
        john.openAccount(new Checking());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", manager.getCustomerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Manager manager = new Manager("Bob", bank);
        Account checkingAccount = new Checking();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, manager.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Manager manager = new Manager("Bob", bank);
        Account checkingAccount = new Savings();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, manager.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Manager manager = new Manager("Bob", bank);
        Account maxiSavingsAccount = new MaxiSavings();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, manager.getTotalInterestPaid(), DOUBLE_DELTA);
    }

}
