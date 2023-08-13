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

    //Testing getCustomerSummary output
    @Test
    public void getCustomerSummary() {
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

    //Testing getTotalInterestPaid output for a 'CHECKING' Account
    @Test
    public void getTotalInterestPaidChecking() {
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

    //Testing getTotalInterestPaid output for a 'SAVINGS' Account
    @Test
    public void getTotalInterestPaidSavings() {
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

    //Testing getTotalInterestPaid output for a 'MAXI_SAVINGS' Account
    @Test
    public void getTotalInterestPaidMaxiSavings() {
        //Open a Bank
        Bank bank = new Bank();
        //Create a new customer with the name 'Bill'
        Customer bill = new Customer("Bill");
        //Add 'Bill' to the bank
        bank.addCustomer(bill);
        //Open a 'SAVINGS' account for 'Bill'
        Account maxiSavings = bill.openAccount(AccountType.MAXI_SAVINGS);
        

        //Deposit '3000'
        maxiSavings.tryDeposit(3000.0);
        //Add interest
        maxiSavings.addInterest();

        System.out.println(maxiSavings.getAccruedInterest());

        //Compare the expected results to the getTotalInterestPaid() function.
        assertEquals(170.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }


}
