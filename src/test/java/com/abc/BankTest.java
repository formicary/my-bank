package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        assertEquals(0.1, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(4.0, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(4000.0);
        
        assertEquals(204.0, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_withdrawal() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(4000.0);
        checkingAccount.withdraw(1000.0);
        
        assertEquals(3.0, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }
    
    @Test
    public void get_first_customer() {
    	Bank bank = new Bank();
    	Customer jane = new Customer("Jane");
    	Customer jill = new Customer("Jill");
    	bank.addCustomer(jane);
    	bank.addCustomer(jill);
    	
    	assertEquals("Jane", bank.getFirstCustomer());
    }

}
