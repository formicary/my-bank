package com.abc.report;

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
        Account checkingAccount = new SavingsAccount();
        Customer bill = new Customer("Bill");
        bill.addAccount(checkingAccount);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(bill);

        try{
            checkingAccount.processTransaction(new Transaction(new Money("1500")));
        } catch(InsufficientBalanceException e) {
            fail("exception thrown unexpectedly");
        }

        assertEquals(new Money("2"), new InterestReport(customers).totalInterestPaid());
    }

    @Test
    public void maxi_savings_account() {
        Account checkingAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill");
        bill.addAccount(checkingAccount);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(bill);

        try{
            checkingAccount.processTransaction(new Transaction(new Money("3000")));
        } catch(InsufficientBalanceException e) {
            fail("exception thrown unexpectedly");
        }

        assertEquals(new Money("170"), new InterestReport(customers).totalInterestPaid());
    }

}