package com.abc;

import org.junit.Test;

import com.abc.Account.*;
import com.abc.TestUtils;
import static org.junit.Assert.assertEquals;

public class BankTest {

    @Test
    public void customerSummaryOne() 
    {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void customerSummaryMultiple() 
    {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        bill.openAccount(new CheckingAccount());
        bill.openAccount(new MaxiSavingsAccount());
        
        bank.addCustomer(bill);

        assertEquals("Customer Summary\n - Bill (2 accounts)", bank.customerSummary());
    }
    
    @Test
    public void checkingAccountInterests() 
    {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.0, bank.totalInterestPaid(), TestUtils.DELTA_MONEY);
    }

    @Test
    public void savingsAccountInterests() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(0.0, bank.totalInterestPaid(), TestUtils.DELTA_MONEY);
    }

    @Test
    public void maxiSavingsAccountInterests() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(0.0, bank.totalInterestPaid(), TestUtils.DELTA_MONEY);
    }

}
