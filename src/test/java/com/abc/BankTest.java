package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.CheckingAccount;
import com.abc.Accounts.MaxiSavingsAccount;
import com.abc.Accounts.SavingsAccount;
import com.abc.util.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    // TODO add extra customer summary tests
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }



}
