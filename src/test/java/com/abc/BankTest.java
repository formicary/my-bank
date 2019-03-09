package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
    	IBank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new SavingsAccount());
        Customer henry = new Customer("Henry");
        henry.openAccount(new CheckingAccount());
        henry.openAccount(new SavingsAccount());
        henry.openAccount(new Maxi_SavingsAccount());
        bank.addCustomer(john);
        bank.addCustomer(oscar);
        bank.addCustomer(henry);

        assertEquals("Customer Summary\n - John (1 account)\n - Oscar (2 accounts)\n - Henry (3 accounts)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        IBank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(1000);
        checkingAccount.deposit(1000);

        checkingAccount.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        checkingAccount.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        DecimalFormat df = new DecimalFormat("##.#####");
        
        double total = bank.totalInterestPaid();
        String temp = df.format(total);
        total = Double.parseDouble(temp);

        assertEquals(0.00548,total, DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        IBank bank = new Bank();
        Account savingsAccount = new SavingsAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);
        
        savingsAccount.deposit(1000);
        savingsAccount.deposit(2000);
        savingsAccount.deposit(1000);
        

        savingsAccount.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        savingsAccount.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        savingsAccount.getTransactions().get(2).setTransactionDate(2019,1,4,0,0,0);
        DecimalFormat df = new DecimalFormat("##.#####");
        
        double total = bank.totalInterestPaid();
        String temp = df.format(total);
        total = Double.parseDouble(temp);
        
        assertEquals(0.01918, total, DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        IBank bank = new Bank();
        Account maxi_SavingsAccount = new Maxi_SavingsAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount(maxi_SavingsAccount);
        bank.addCustomer(bill);
        
        maxi_SavingsAccount.deposit(1000);
        maxi_SavingsAccount.deposit(2000);
        maxi_SavingsAccount.deposit(1000);
        

        maxi_SavingsAccount.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        maxi_SavingsAccount.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        maxi_SavingsAccount.getTransactions().get(2).setTransactionDate(2019,1,4,0,0,0);
        DecimalFormat df = new DecimalFormat("##.#####");
        
        double total = bank.totalInterestPaid();
        String temp = df.format(total);
        total = Double.parseDouble(temp);
        
        assertEquals(0.68499, total, DOUBLE_DELTA);
    }
    @Test
    public void getfirstcustomers() {
        IBank bank = new Bank();

        Customer bill = new Customer("Bill");

        bank.addCustomer(bill);
        ICustomer getfirst = bank.getFirstCustomer();
        
        String firstcustomerName = getfirst.getName();
        assertEquals("Bill", firstcustomerName);
    }
    @Test
    public void AllAccountinterest() {
        IBank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxi_SavingsAccount = new Maxi_SavingsAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bill.openAccount(savingsAccount);
        bill.openAccount(maxi_SavingsAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(1000);
        checkingAccount.deposit(1000);

        checkingAccount.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        checkingAccount.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        
        savingsAccount.deposit(1000);
        savingsAccount.deposit(2000);
        savingsAccount.deposit(1000);
        
        savingsAccount.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        savingsAccount.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        savingsAccount.getTransactions().get(2).setTransactionDate(2019,1,4,0,0,0);
        
        maxi_SavingsAccount.deposit(1000);
        maxi_SavingsAccount.deposit(2000);
        maxi_SavingsAccount.deposit(1000);
        
        maxi_SavingsAccount.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        maxi_SavingsAccount.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        maxi_SavingsAccount.getTransactions().get(2).setTransactionDate(2019,1,4,0,0,0);
        DecimalFormat df = new DecimalFormat("##.#####");
        
        double total = bank.totalInterestPaid();
        String temp = df.format(total);
        total = Double.parseDouble(temp);

        assertEquals(0.70965,total, DOUBLE_DELTA);
    }

    
}
