package com.abc.report;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;
import com.abc.Account.MaxiSavingsAccount;
import com.abc.Account.SavingsAccount;
import com.abc.Bank;
import com.abc.Customer.Customer;
import com.abc.Exception.InsufficientBalanceException;
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
            fail("Exception thrown unexpectedly");
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
            fail("Exception thrown unexpectedly");
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
            fail("Exception thrown unexpectedly");
        }

        assertEquals(new Money("170"), new InterestReport(customers).totalInterestPaid());
    }

}