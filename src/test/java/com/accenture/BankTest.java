package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.accounts.CheckingAccount;
import com.accenture.accounts.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;


    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        CheckingAccount checkingAccount = new CheckingAccount(11111111);
        bank.addCustomer(john);
        bank.addAccount(checkingAccount);
        bank.linkCustomerToAccount(john,checkingAccount);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummary_2Accounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        CheckingAccount checkingAccount1 = new CheckingAccount(11111111);
        CheckingAccount checkingAccount2 = new CheckingAccount(22222222);
        bank.addCustomer(john);
        bank.addAccount(checkingAccount1);
        bank.addAccount(checkingAccount2);
        bank.linkCustomerToAccount(john,checkingAccount1);
        bank.linkCustomerToAccount(john,checkingAccount2);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummary_0Accounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (0 accounts)", bank.customerSummary());
    }


    @Test
    public void testAddCustomer() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("John"));

        assertEquals(1, bank.getCustomers().size());
    }

    @Test
    public void testAddCustomer2() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("John"));
        bank.addCustomer(new Customer("George"));

        assertEquals(2, bank.getCustomers().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCustomer_Exception() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("John"));
        bank.addCustomer(new Customer("John"));

    }


    @Test
    public void testAddAccount() {
        Bank bank = new Bank();
        bank.addAccount(new SavingsAccount(12345678));

        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    public void testAddAccount2() {
        Bank bank = new Bank();
        bank.addAccount(new SavingsAccount(11111111));
        bank.addAccount(new SavingsAccount(22222222));

        assertEquals(2, bank.getAccounts().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAccount_Exception() {
        Bank bank = new Bank();
        bank.addAccount(new SavingsAccount(11111111));
        bank.addAccount(new SavingsAccount(11111111));

    }

    @Test
    public void testTotalInterestPaid_1Customer(){
        Bank bank = new Bank();
        Customer customer = new Customer("George");
        Account account = new SavingsAccount(12345678);
        bank.addCustomer(customer);
        bank.addAccount(account);
        bank.linkCustomerToAccount(customer,account);

        account.deposit(1000,"Regular Deposit");

        bank.dailyInterestPay();

        double totalInterestPaid = bank.totalInterestPaid();
        double expected = ((double)1/365);

        assertEquals(expected,totalInterestPaid,DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaid_2Customers(){
        Bank bank = new Bank();
        Customer customer1 = new Customer("George");
        Customer customer2 = new Customer("Vouras");

        Account account1 = new SavingsAccount(11111111);
        Account account2 = new SavingsAccount(22222222);

        bank.addCustomer(customer1);
        bank.addCustomer(customer2);

        bank.addAccount(account1);
        bank.addAccount(account2);

        account1.deposit(1000,"Regular Deposit");
        account2.deposit(1000,"Regular Deposit");

        bank.dailyInterestPay();

        double expected = ((double)2/365);

        double totalInterestPaid = bank.totalInterestPaid();
        assertEquals(expected,totalInterestPaid,DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaid_3Customers(){
        Bank bank = new Bank();
        Customer customer1 = new Customer("George");
        Customer customer2 = new Customer("Paraschos");
        Customer customer3 = new Customer("Vouras");

        Account account1 = new SavingsAccount(11111111);
        Account account2 = new SavingsAccount(22222222);
        Account account3 = new SavingsAccount(33333333);

        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        bank.addCustomer(customer3);

        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(account3);

        bank.linkCustomerToAccount(customer1,account1);
        bank.linkCustomerToAccount(customer2,account2);
        bank.linkCustomerToAccount(customer3,account3);

        account1.deposit(1000,"Regular Deposit");
        account2.deposit(1000,"Regular Deposit");
        account3.deposit(1000,"Regular Deposit");

        bank.dailyInterestPay();
        double expected = ((double)3/365);
        double totalInterestPaid = bank.totalInterestPaid();
        assertEquals(expected,totalInterestPaid,DOUBLE_DELTA);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testLinkCustomerToAccount_CustomerException(){
        Bank bank = new Bank();
        Account account = new SavingsAccount(12345678);
        bank.addAccount(account);
        bank.linkCustomerToAccount(new Customer("George"),account);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLinkCustomerToAccount_AccountException(){
        Bank bank = new Bank();
        Customer customer = new Customer("George");
        Account account = new SavingsAccount(12345678);
        bank.addCustomer(customer);

        bank.linkCustomerToAccount(customer,account);
    }



}
