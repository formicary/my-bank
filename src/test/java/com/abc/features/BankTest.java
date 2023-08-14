package com.abc.features;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.abc.classes.Account;
import com.abc.classes.Account.AccountType;
import com.abc.classes.Bank;
import com.abc.classes.Customer;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    //Testing getCustomerSummary output//
    @Test
    public void getCustomerSummaryTest() {
        //Open a Bank
        Bank bank = new Bank();
        //Create a new customer with the name 'John'
        Customer john = new Customer("John");
        //Open a 'CHECKING' account for 'John'
        john.openAccount(AccountType.CHECKING);
        //Add 'John' to the bank
        bank.addCustomer(john);
        
        //Compare the expected results to the getCustomerSummary() function.
        assertEquals("Customer Summary\n - John: [1 account(s) open]", bank.getCustomerSummary());
    }


    //Testing getCustomerSummary output when no customers are in the bank//
    @Test
    public void getCustomerSummaryNoCustomersTest() {
        //Open a Bank
        Bank bank = new Bank();
        
        //Compare the expected results to the getCustomerSummary() function.
        assertEquals("No customers found", bank.getCustomerSummary());
    }


    //Testing getTotalInterestPaid output for a 'CHECKING' Account//
    @Test
    public void getTotalInterestPaidCheckingTest() {
        //Open a Bank
        Bank bank = new Bank();
        //Create a new customer with the name 'Bill'
        Customer bill = new Customer("Bill");
        //Open a 'CHECKING' account for 'Bill'
        Account checking = bill.openAccount(AccountType.CHECKING);
        //Add 'Bill' to the bank
        bank.addCustomer(bill);
        

        //Deposit '100'
        checking.tryDeposit(100.0);
        //Add interest to the account
        checking.addInterest();

        //Compare the expected results to the getTotalInterestPaid() function.
        assertEquals(0.1, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }


    //Testing getTotalInterestPaid output for a 'SAVINGS' Account//
    @Test
    public void getTotalInterestPaidSavingsTest() {
        //Open a Bank
        Bank bank = new Bank();
        //Create a new customer with the name 'Bill'
        Customer bill = new Customer("Bill");
        //Add 'Bill' to the bank
        bank.addCustomer(bill);
        //Open a 'SAVINGS' account for 'Bill'
        Account savings = bill.openAccount(AccountType.SAVINGS);
        
        
        //Deposit '1500'
        savings.tryDeposit(1500.0);
        //Add interest
        savings.addInterest();

        //Compare the expected results to the getTotalInterestPaid() function.
        assertEquals(2.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }


    //Testing getTotalInterestPaid output for a 'MAXI_SAVINGS' Account//
    @Test
    public void getTotalInterestPaidMaxiSavingsTest() {
        //Open a Bank
        Bank bank = new Bank();
        //Create a new customer with the name 'Bill'
        Customer bill = new Customer("Bill");
        //Add 'Bill' to the bank
        bank.addCustomer(bill);
        //Open a 'MAXI_SAVINGS' account for 'Bill'
        Account maxiSavings = bill.openAccount(AccountType.MAXI_SAVINGS);
        

        //Deposit '3000'
        maxiSavings.tryDeposit(3000.0);
        //Add interest
        maxiSavings.addInterest();

        //Compare the expected results to the getTotalInterestPaid() function.
        assertEquals(170.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    //Testing getTotalInterestPaid across multiple accounts//
    @Test
    public void getTotalInterestEarnedMultipleTest(){
        //Open a Bank
        Bank bank = new Bank();
        //Create a new customer with the name 'Bill'
        Customer bill = new Customer("Bill");
        //Add 'Bill' to the bank
        bank.addCustomer(bill);

        //Open multiple different accounts, deposit various amounts
        Account maxiSavings = bill.openAccount(AccountType.MAXI_SAVINGS);
        maxiSavings.tryDeposit(2500);
        Account checking = bill.openAccount(AccountType.CHECKING);
        checking.tryDeposit(500);
        Account savings = bill.openAccount(Account.AccountType.SAVINGS);
        savings.tryDeposit(3500);

        //Pay interest to all accounts in the bank
        bank.payInterestToAllAccounts();

        assertEquals(126.5, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }


}
