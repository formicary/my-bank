package com.abc;

import org.junit.Test;

import com.abc.account.AccountI;
import com.abc.account.Checking;
import com.abc.account.MaxiSaving;
import com.abc.account.Saving;

import static org.junit.Assert.assertEquals;

public class BankTest {
	
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Checking());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        AccountI checkingAccount = new Checking();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        AccountI Saving = new Saving();
        bank.addCustomer(new Customer("Bill").openAccount(Saving));

        Saving.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
   
    
    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        AccountI MaxiSavingAccount = new MaxiSaving();
        bank.addCustomer(new Customer("Bill").openAccount(MaxiSavingAccount));

        MaxiSavingAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
