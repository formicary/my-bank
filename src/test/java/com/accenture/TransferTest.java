package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.accounts.CheckingAccount;
import com.accenture.accounts.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransferTest {

    double DOUBLE_DELTA = 1e-15;

    @Test
    public void testTransaction() throws Exception {
        Bank bank = new Bank();
        Customer customer = new Customer("George");
        Account accountFrom = new CheckingAccount(11111111);
        Account accountTo = new SavingsAccount(22222222);

        bank.addCustomer(customer);
        bank.addAccount(accountFrom);
        bank.addAccount(accountTo);
        bank.linkCustomerToAccount(customer,accountFrom);
        bank.linkCustomerToAccount(customer,accountTo);

        accountFrom.deposit(1000,"Regular Deposit");

        bank.transferMoney(accountFrom,accountTo,500);
        assertEquals(500,accountFrom.sumTransactions(),DOUBLE_DELTA);
        assertEquals(500,accountTo.sumTransactions(),DOUBLE_DELTA);

    }

    @Test(expected = Exception.class)
    public void testTransaction_Exception() throws Exception {
        Bank bank = new Bank();
        Customer customer1 = new Customer("George");
        Customer customer2 = new Customer("Vouras");
        Account accountFrom = new CheckingAccount(11111111);
        Account accountTo = new SavingsAccount(22222222);

        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        bank.addAccount(accountFrom);
        bank.addAccount(accountTo);
        bank.linkCustomerToAccount(customer1,accountFrom);
        bank.linkCustomerToAccount(customer2,accountTo);

        accountFrom.deposit(1000,"Regular Deposit");

        bank.transferMoney(accountFrom,accountTo,500);
        assertEquals(500,accountFrom.sumTransactions(),DOUBLE_DELTA);
        assertEquals(500,accountTo.sumTransactions(),DOUBLE_DELTA);

    }
}
