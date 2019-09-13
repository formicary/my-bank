package com.abc;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;
import com.abc.Account.MaxiSavingsAccount;
import com.abc.Account.SavingsAccount;
import com.abc.Customer.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.addAccount(new CheckingAccount());

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.addAccount(checkingAccount);

        try{
            checkingAccount.processTransaction(new Transaction(100));
        } catch(Exception e) {
            fail("Exception thrown unexpectedly");
        }

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill).addAccount(checkingAccount);

        try{
            checkingAccount.processTransaction(new Transaction(1500));
        } catch(Exception e) {
            fail("Exception thrown unexpectedly");
        }

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill).addAccount(checkingAccount);

        try{
            checkingAccount.processTransaction(new Transaction(3000));
        } catch(Exception e) {
            fail("Exception thrown unexpectedly");
        }

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
