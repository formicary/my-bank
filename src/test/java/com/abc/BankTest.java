package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankTest {

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        String iban = "123";
        john.openAccount(new Account(iban, Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        String iban = "123";
        Account checkingAccount = new Account(iban, Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(0.1).compareTo(bank.totalInterestPaid()));
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        String iban = "123";
        Account checkingAccount = new Account(iban, Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(BigDecimal.valueOf(1500));

        assertEquals(0, BigDecimal.valueOf(2).compareTo(bank.totalInterestPaid()));
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        String iban = "123";
        Account checkingAccount = new Account(iban, Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(BigDecimal.valueOf(3000));

        assertEquals(0, BigDecimal.valueOf(150).compareTo(bank.totalInterestPaid()));
    }

}
