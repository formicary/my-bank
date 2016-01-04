package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
  private static final double DELTA = 1e-15;
  private Bank bank = new Bank();
  private Customer bill = new Customer("Bill");

    @Test
    public void customerSummary() {
        bill.openAccount(new Account(bill,Account.CHECKING));
        bank.addCustomer(bill);
        assertEquals("Customer Summary\n - Bill (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Account checkingAccount = new Account(bill,Account.CHECKING);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(100.0);
        assertEquals(0.1, bank.totalInterestPaid(), DELTA);
    }

    @Test
    public void savings_account() {
        Account checkingAccount = new Account(bill,Account.SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));
        checkingAccount.deposit(1500.0);
        assertEquals(2.0, bank.totalInterestPaid(), DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Account checkingAccount = new Account(bill,Account.MAXI_SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));
        checkingAccount.deposit(3000.0);
        assertEquals(3000*0.05, bank.totalInterestPaid(), DELTA);
    }

    @Test
    public void returnsFirstCustomerCorrectly() {
      assertEquals(bank.getFirstCustomer(),"Error: No Customers Exist");
      bank.addCustomer(bill);
      Customer gary = new Customer("Gary");
      bank.addCustomer(gary);
      assertEquals(bank.getFirstCustomer(),"Bill");
    }
}
