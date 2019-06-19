package test.java.com.abc;

import org.junit.Test;

import main.java.com.abc.Account;
import main.java.com.abc.Bank;
import main.java.com.abc.Customer;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.junit.Ignore;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private static final DecimalFormat df2 = new DecimalFormat("#.##");

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    //Old Interest Test
    @Ignore
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    // Savings before compound interest
    @Ignore
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //Maxi Savings interest changed 
    @Ignore
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //Maxi Savings before compound interest 
    @Ignore
    public void maxi_savings_account_one() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        checkingAccount.withdraw(100.0, 5);
        //2900 in the bank 5 days ago so interest today should be 0.1% ie 2900 * 0.001 = 2.90

        assertEquals(2.9, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //Maxi Savings before compound interest 
    @Ignore
    public void maxi_savings_account_two() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        checkingAccount.withdraw(100.0, 10);
        //2900 in the bank 10 days ago so interest today should be 5% ie 2900 * 0.05 = 145

        assertEquals(145.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    
    
    //Tests after adding compound Interest
    
    @Test
    public void checkingAccount_CompoundInterest() throws ParseException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1000.0);
        checkingAccount.interestEarned(100);
        assertEquals(0.27, Double.parseDouble(df2.format(bank.totalInterestPaid())), DOUBLE_DELTA);
    }
    
    @Test
    public void savings_account_CompoundInterest() throws ParseException {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);
        savingsAccount.interestEarned(100);
        assertEquals(0.55, Double.parseDouble(df2.format(bank.totalInterestPaid())), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_CompoundInterest() throws ParseException {
    	
    	
    	Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3100.0);
        checkingAccount.withdraw(100.0, 5);
        //5 days of 0.001 then 0.05
        checkingAccount.interestEarned(100);
        assertEquals(39.34, Double.parseDouble(df2.format(bank.totalInterestPaid())), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_CompoundInterest_Two() throws ParseException {
    	
    	
    	Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3100.0);
        checkingAccount.withdraw(100.0, 10);
        //0.05 for all days
        checkingAccount.interestEarned(100);
        
        assertEquals(41.38, Double.parseDouble(df2.format(bank.totalInterestPaid())), DOUBLE_DELTA);
    }
}
