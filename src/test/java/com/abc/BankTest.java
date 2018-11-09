package com.abc;

import java.util.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    double checkingV = 100.0;
    double SavingsV = 1500.0;
    double MaxiSV = 3000.0;
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(checkingV);
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void checkingAccount2() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(2000.0);
        checkingAccount.withdraw(750.0);
        assertEquals(1.25, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account SavingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(SavingsAccount));

        SavingsAccount.deposit(SavingsV);
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void savings_account2() {
        Bank bank = new Bank();
        Account SavingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(SavingsAccount));
        SavingsAccount.deposit(SavingsV);
        SavingsAccount.withdraw(150.0);
        assertEquals(1.7, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account MaxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(MaxiAccount));
        MaxiAccount.deposit(MaxiSV);
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        
    }
    @Test
    public void maxi_savings_account2() {
        Bank bank = new Bank();
        Account MaxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(MaxiAccount));
        MaxiAccount.deposit(MaxiSV);
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        MaxiAccount.deposit(200.0);
        MaxiAccount.withdraw(200.0);
        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void testForDailyIntrest(){
        Bank B = new Bank();
        Account MaxiAccount = new Account(Account.MAXI_SAVINGS);
        B.addCustomer(new Customer("Bill").openAccount(MaxiAccount));
        
        MaxiAccount.deposit(MaxiSV);
        MaxiAccount.withdraw(300);
        assertEquals(2700.0, MaxiAccount.ACCTotal(),DOUBLE_DELTA);
                
        MaxiAccount.DailyIntrestcalc();
        assertEquals(2700.0 + ((2700.0*0.001)/365), MaxiAccount.ACCTotal(),DOUBLE_DELTA);
    }

}
