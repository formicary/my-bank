package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {
  
  private static final double DOUBLE_DELTA = 1e-15;
  
  @Test
  public void customerName() {
    String name = "Henry";
    Customer henry = new Customer(name);
    assertEquals(name, henry.getName());
  }
  
  @Test
  public void oneAccount() {
    Customer oscar = new Customer("Oscar").openAccount(new Account(
        Account.Type.SAVINGS));
    assertEquals(1, oscar.getNumberOfAccounts());
  }
  
  @Test
  public void twoAccount() {
    Customer oscar = new Customer("Oscar").openAccount(new Account(
        Account.Type.SAVINGS));
    oscar.openAccount(new Account(Account.Type.CHECKING));
    assertEquals(2, oscar.getNumberOfAccounts());
  }
  
  @Test
  public void threeAccounts() {
    Customer oscar = new Customer("Oscar").openAccount(new Account(
        Account.Type.SAVINGS));
    oscar.openAccount(new Account(Account.Type.CHECKING));
    oscar.openAccount(new Account(Account.Type.MAXI_SAVINGS));
    assertEquals(3, oscar.getNumberOfAccounts());
  }
  
  @Test
  public void interestEarnedNoAccounts() {
    Customer henry = new Customer("Henry");
    assertEquals(0.0, henry.totalInterestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void interestEarnedOneAccount() {
    Account checkingAccount = new Account(Account.Type.CHECKING);
    Customer henry = new Customer("Henry").openAccount(checkingAccount);
    checkingAccount.deposit(200.0);
    assertEquals(0.2, henry.totalInterestEarned(), DOUBLE_DELTA);
  }

  @Test
  public void statement() {

    Account checkingAccount = new Account(Account.Type.CHECKING);
    Account savingsAccount = new Account(Account.Type.SAVINGS);

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
