package com.abc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private static final TestOnlyCurrentTime testOnlyCurrentTime = TestOnlyCurrentTime.getInstance();
    
    @Test
    public void init() {
		Date date = Calendar.getInstance().getTime();
		testOnlyCurrentTime.setDate(date);
    }


    @Test
    public void customerSummary_SingleAccountGiven_PrintJohnAccount() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void customerSummary_TwoAccountsGiven_PrintJohnAndMaxAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        Customer max = new Customer("Max");
        max.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(max);
        
        assertEquals("Customer Summary\n - John (1 account)\n - Max (1 account)", bank.customerSummary());
    }
    
    @Test
    public void customerSummary_NoAccountsGiven_PrintNoAccounts() {
        Bank bank = new Bank();
     
        assertEquals("Customer Summary", bank.customerSummary());
    }

    @Test
    public void checkingAccount_OneTransaction_ReturnCorrectInterestPaid() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        
        Calendar calendar = new GregorianCalendar(2018, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        checkingAccount.deposit(100.0);
        
        calendar = new GregorianCalendar(2019, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());
                       
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount_OneTransaction_ReturnCorrectInterestPaid() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        
        Calendar calendar = new GregorianCalendar(2018, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        savingsAccount.deposit(1500.0);
        
        calendar = new GregorianCalendar(2019, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void savingsAccount_TwoDepositsSameDayGiven_ReurnCorrectInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        
        Calendar calendar = new GregorianCalendar(2018, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        savingsAccount.deposit(1500.0);
        savingsAccount.deposit(1500.0);
        
        calendar = new GregorianCalendar(2019, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        assertEquals(6.01, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void savingsAccount_TwoDepositsDifferentDayGiven_ReurnCorrectInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);
        
        Calendar calendar = new GregorianCalendar(2018, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        savingsAccount.deposit(1500.0);
        
        calendar = new GregorianCalendar(2018, 7, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());
        
        savingsAccount.deposit(1500.0);
        
        calendar = new GregorianCalendar(2019, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        assertEquals(5.50, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void savingsAccount_DepositsAcrossInterestRateBoundary_ReurnCorrectInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);
        
        Calendar calendar = new GregorianCalendar(2018, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        savingsAccount.deposit(500.0);
        
        calendar = new GregorianCalendar(2018, 7, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());
        
        savingsAccount.deposit(600.0);
        
        calendar = new GregorianCalendar(2019, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        assertEquals(1.92, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount_OneDepositYearInterest_ReturnCorrectInterest() {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavings));
        
        Calendar calendar = new GregorianCalendar(2018, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        maxiSavings.deposit(3000.0);
        
        calendar = new GregorianCalendar(2019, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        // Using new MaxiSavings Interest Rate
        assertEquals(153.8, bank.totalInterestPaid(), DOUBLE_DELTA); 
    }
    
    @Test
    public void maxiSavingsAccount_WithdrawWithin10DaysAgo_ReturnCorrectInterest() {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavings));
        
        Calendar calendar = new GregorianCalendar(2019, 5, 25);
        testOnlyCurrentTime.setDate(calendar.getTime());

        maxiSavings.deposit(3000.0);
        maxiSavings.withdraw(200.0);
        
        calendar = new GregorianCalendar(2019, 5, 30);
        testOnlyCurrentTime.setDate(calendar.getTime());

        // Using new MaxiSavings Interest Rate
        assertEquals(0.04, bank.totalInterestPaid(), DOUBLE_DELTA); 
    }
    
    
    
    @Test
    public void getFirstCustomer_OneCustomerGiven_ShouldReturnJohn() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("John", bank.getFirstCustomer());
    }
    
    @Test
    public void getFirstCustomer_TwoCustomersGiven_ShouldReturnJohn() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        
        Customer max = new Customer("Max");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(max);

        assertEquals("John", bank.getFirstCustomer());
    }
    
    @Test
    public void getFirstCustomer_NoCustomersGiven_ShouldReturnNull() {
        Bank bank = new Bank();
        assertEquals(null, bank.getFirstCustomer());
    }

}
