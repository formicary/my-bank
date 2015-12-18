package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {
  
  private static final double DOUBLE_DELTA = 1e-15;
  
  @Test
  public void testCustomerName() {
    String name = "Henry";
    Customer henry = new Customer(name);
    assertEquals(name, henry.getName());
  }
  
  @Test
  public void testOneAccount() {
    Customer oscar = new Customer("Oscar").openAccount(new Account(
        Account.SAVINGS));
    assertEquals(1, oscar.getNumberOfAccounts());
  }
  
  @Test
  public void testTwoAccount() {
    Customer oscar = new Customer("Oscar").openAccount(new Account(
        Account.SAVINGS));
    oscar.openAccount(new Account(Account.CHECKING));
    assertEquals(2, oscar.getNumberOfAccounts());
  }
  
  @Test
  public void testThreeAccounts() {
    Customer oscar = new Customer("Oscar").openAccount(new Account(
        Account.SAVINGS));
    oscar.openAccount(new Account(Account.CHECKING));
    oscar.openAccount(new Account(Account.MAXI_SAVINGS));
    assertEquals(3, oscar.getNumberOfAccounts());
  }
  
  @Test
  public void testInterestEarnedNoAccounts() {
    Customer henry = new Customer("Henry");
    assertEquals(0.0, henry.totalInterestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void testInterestEarnedOneAccount() {
    Account checkingAccount = new Account(Account.CHECKING);
    Customer henry = new Customer("Henry").openAccount(checkingAccount);
    checkingAccount.deposit(200.0);
    assertEquals(0.2, henry.totalInterestEarned(), DOUBLE_DELTA);
  }

  @Test
  public void testStatement() {

    Account checkingAccount = new Account(Account.CHECKING);
    Account savingsAccount = new Account(Account.SAVINGS);

    Customer henry = new Customer("Henry").openAccount(checkingAccount)
        .openAccount(savingsAccount);

    checkingAccount.deposit(100.0);
    savingsAccount.deposit(4000.0);
    savingsAccount.withdraw(200.0);

    assertEquals("Statement for Henry\n" 
        + "\n" 
        + "Checking Account\n"
        + "  deposit $100.00\n" 
        + "Total $100.00\n" 
        + "\n"
        + "Savings Account\n" 
        + "  deposit $4,000.00\n"
        + "  withdrawal $200.00\n" 
        + "Total $3,800.00\n" 
        + "\n"
        + "Total In All Accounts $3,900.00", henry.getStatement());
  }
  
}
