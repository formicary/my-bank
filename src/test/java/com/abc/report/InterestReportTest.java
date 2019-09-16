package com.abc.report;

import com.abc.DateProvider;
import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;
import com.abc.customer.Customer;
import com.abc.exception.InsufficientBalanceException;
import com.abc.Money;
import com.abc.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class InterestReportTest {
    @Test
    public void checkingAccount() {
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill");
        bill.addAccount(checkingAccount);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(bill);
        try{
            checkingAccount.processTransaction(new Transaction(new Money("100")));
        } catch(InsufficientBalanceException e) {
            fail("exception thrown unexpectedly");
        }
        assertEquals(new Money("0.1"), new InterestReport(customers).totalInterestPaid());
    }

    @Test
    public void savings_account() {
        Account savingsAcc = new SavingsAccount();
        Customer bill = new Customer("Bill");
        bill.addAccount(savingsAcc);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(bill);

        try{
            savingsAcc.processTransaction(new Transaction(new Money("1500")));
        } catch(InsufficientBalanceException e) {
            fail("exception thrown unexpectedly");
        }

        assertEquals(new Money("2"), new InterestReport(customers).totalInterestPaid());
    }

    @Test
    public void maxi_savings_account() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill");
        bill.addAccount(maxiSavingsAccount);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(bill);

        try{
            maxiSavingsAccount.processTransaction(new Transaction(new Money("3000")));
        } catch(InsufficientBalanceException e) {
            fail("exception thrown unexpectedly");
        }

        assertEquals(new Money("3"), new InterestReport(customers).totalInterestPaid());
    }

    // figure out how to change time for testing
    @Test
    public void maxi_savings_account_more_than_ten_days() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill");
        bill.addAccount(maxiSavingsAccount);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(bill);

        try{
            maxiSavingsAccount.processTransaction(new Transaction(new Money("3000")));
        } catch(InsufficientBalanceException e) {
            fail("exception thrown unexpectedly");
        }

        Date now = DateProvider.getInstance().now();
        now.setTime(now.getTime()- 10 * 86400000);
        assertEquals(new Money("3"), new InterestReport(customers).totalInterestPaid());
    }

}