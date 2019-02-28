package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/*----------------------------------------------------------------------------- 
                            Tests for Banks
-----------------------------------------------------------------------------*/
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        assertEquals("Error! Bank has no customers", bank.getFirstCustomer());

        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
        assertEquals("John", bank.getFirstCustomer());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savings = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(savings));

        savings.deposit(1500.0);
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxi = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxi));

        maxi.deposit(3000.0);
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
