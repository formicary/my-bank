package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openCheckingAccount();
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummaryMultipleAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openCheckingAccount();
        john.openSavingsAccount();
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void customerSummaryMultipleCustomers(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openCheckingAccount();
        bank.addCustomer(john);

        Customer brian = new Customer("Brian");
        brian.openCheckingAccount();
        bank.addCustomer(brian);

        assertEquals("Customer Summary\n - John (1 account)\n - Brian (1 account)", bank.customerSummary());

    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account checkingAccount = bill.openCheckingAccount();
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account savingsAccount = bill.openSavingsAccount();
        bank.addCustomer(bill);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Ignore
    public void maxi_savings_account_old() {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        Account maxiSavingsAccount = bill.openMaxiSavingsAccount();

        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestMultipleAccounts(){
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        Account checking = bill.openCheckingAccount();
        Account saver = bill.openSavingsAccount();
        Account maxi = bill.openMaxiSavingsAccount();

        bank.addCustomer(bill);

        checking.deposit(100.00); // earns 0.1 interest
        saver.deposit(1500.00); // earns 2.0 interest
        maxi.deposit(3000.00); // earns 150.0 interest

        assertEquals(152.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void interestMultipleCustomers(){
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        Customer ted = new Customer("Ted");

        Account billSaver = bill.openSavingsAccount();
        Account tedChecking = ted.openCheckingAccount();

        billSaver.deposit(1500.00); // = $2 interest
        tedChecking.deposit(2500.00); // = 2.5 interest

        bank.addCustomer(bill);
        bank.addCustomer(ted);

        assertEquals(4.50, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
