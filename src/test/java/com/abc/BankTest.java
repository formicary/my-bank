package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Date;


public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccountPORTFOLIO_1() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0, new Date());

        assertEquals(0.1, bank.totalInterestPaid(Customer.AccountPortfolioVersion.PORTFOLIO_1), DOUBLE_DELTA);
    }

    @Test
    public void checkingAccountPORTFOLIO_2() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0, new Date());

        assertEquals(0.1, bank.totalInterestPaid(Customer.AccountPortfolioVersion.PORTFOLIO_2), DOUBLE_DELTA);
    }

    @Test
    public void savings_accountPORTFOLIO_1() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0, new Date());

        assertEquals(2.0, bank.totalInterestPaid(Customer.AccountPortfolioVersion.PORTFOLIO_1), DOUBLE_DELTA);
    }

    @Test
    public void savings_accountPORTFOLIO_2() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0, new Date());

        assertEquals(2.0, bank.totalInterestPaid(Customer.AccountPortfolioVersion.PORTFOLIO_2), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_accountPORTFOLIO_1() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0, new Date());

        assertEquals(170.0, bank.totalInterestPaid(Customer.AccountPortfolioVersion.PORTFOLIO_1), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_accountPORTFOLIO_2() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0, new Date());

        assertEquals(150.0, bank.totalInterestPaid(Customer.AccountPortfolioVersion.PORTFOLIO_2), DOUBLE_DELTA);
    }

}
