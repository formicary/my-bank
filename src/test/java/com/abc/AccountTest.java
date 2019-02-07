package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void TestOpenAccount_OneAccount() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        Account account = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(sarah);
        sarah.openAccount(account);
        account.deposit(800);

        assertEquals(1, sarah.getNumberOfAccounts());
    }



    @Test
    public void TestOpenTwoAccounts() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        sarah.openAccount(new Account(1));
        sarah.openAccount(new Account(2));

        assertEquals(2, sarah.getNumberOfAccounts());
    }

}