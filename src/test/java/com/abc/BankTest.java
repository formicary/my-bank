package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
  
  private static final double DOUBLE_DELTA = 1e-15;
  
  @Test
  public void firstCustomerNoCustomers() {
    Bank bank = new Bank();
    assertEquals("No customers", bank.getFirstCustomer());
  }
  
  @Test
  public void firstCustomerOneCustomer() {
    Bank bank = new Bank();
    String name = "Bill";
    bank.addCustomer(new Customer(name));
    assertEquals(name, bank.getFirstCustomer());
  }
  
  @Test
  public void customerSummaryNoCustomers() {
    Bank bank = new Bank();
    assertEquals("Customer Summary", bank.customerSummary());
  }

  @Test
  public void customerSummaryOneCustomer() {
    Bank bank = new Bank();
    Customer john = new Customer("John");
    john.openAccount(new Account(Account.CHECKING));
    bank.addCustomer(john);

    assertEquals("Customer Summary\n - John (1 account)",
        bank.customerSummary());
  }

  @Test
  public void checkingAccountInterestPaid() {
    Bank bank = new Bank();
    Account checkingAccount = new Account(Account.CHECKING);
    Customer bill = new Customer("Bill").openAccount(checkingAccount);
    bank.addCustomer(bill);

    checkingAccount.deposit(100.0);

    assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
  }

  @Test
  public void savingsAccountInterestPaid() {
    Bank bank = new Bank();
    Account savingsAccount = new Account(Account.SAVINGS);
    bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

    savingsAccount.deposit(1500.0);

    assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
  }

  @Test
  public void maxiSavingsAccountInterestPaid() {
    Bank bank = new Bank();
    Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
    bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

    maxiSavingsAccount.deposit(3000.0);

    assertEquals(17.0, bank.totalInterestPaid(), DOUBLE_DELTA);
  }
  
}
