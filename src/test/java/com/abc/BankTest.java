package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

public class BankTest {
    private static final double DOUBLE_DELTA = 0.000001;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
       
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);
        System.out.println(bank.totalInterestPaid());
        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountNormalInterest() {
        
        Account maxiSavingsAccount = new MaxiSavingsAccount(2000.0);
        
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        
        Bank bank = new Bank();
        bank.addCustomer(bill);

        // £2k deposited, in last 10 days so normal interest rate of 0.01% gives £2
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountSpecialInterest() {
    	
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(2000.0, LocalDate.of(2018, 1, 1));
       
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        
        Bank bank = new Bank();
        bank.addCustomer(bill);

        
        // £2k deposited, more than 10 days ago so special interest rate of 0.5% gives £100
        assertEquals(100.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
