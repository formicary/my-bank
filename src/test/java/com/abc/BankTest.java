package com.abc;

import java.util.Date;
import java.util.List;

import main.java.com.abc.Account;
import main.java.com.abc.Bank;
import main.java.com.abc.CheckingsAccount;
import main.java.com.abc.Customer;
import main.java.com.abc.MaxiSavingsAccount;
import main.java.com.abc.SavingsAccount;
import main.java.com.abc.Transaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingsAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingsAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        Date lastYear = new Date(System.currentTimeMillis() - 365L * 24 * 3600
				* 1000);
        List<Transaction> t = checkingAccount.getTransactions();
        t.get(0).setTransactionDate(lastYear);
        

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);
        Date lastYear = new Date(System.currentTimeMillis() - 365L * 24 * 3600
				* 1000);
        List<Transaction> t = checkingAccount.getTransactions();
        t.get(0).setTransactionDate(lastYear);
        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);
        Date lastYear = new Date(System.currentTimeMillis() - 365L * 24 * 3600
				* 1000);
        List<Transaction> t = checkingAccount.getTransactions();
        t.get(0).setTransactionDate(lastYear);
        assertEquals(51.27, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void customerFirst() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingsAccount());
        bank.addCustomer(john);

        assertEquals("John", bank.getFirstCustomer());
    }
}

