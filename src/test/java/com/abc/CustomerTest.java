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
  
  @Test(expected = IllegalArgumentException.class)
  public void transferNoAccounts() {
    Customer harry = new Customer("Harry");
    harry.transfer(0, 0, 300.0);
  }
  
  @Test
  public void transferSameAccount() {
    Account account = new Account(Account.Type.CHECKING);
    Customer harry = new Customer("Harry").openAccount(account);
    harry.transfer(0, 0, 300.0);
    assertEquals(0.0, account.sumTransactions(), DOUBLE_DELTA);
  }
  
  @Test
  public void transferTwoAccounts() {
    Account first = new Account(Account.Type.SAVINGS);
    Account second = new Account(Account.Type.CHECKING);
    Customer harry = new Customer("Harry").openAccount(first).openAccount(
        second);
    first.transact(500.0);
    harry.transfer(0, 1, 300.0);
    assertEquals(200.0, first.sumTransactions(), DOUBLE_DELTA);
    assertEquals(300.0, second.sumTransactions(), DOUBLE_DELTA);
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
    checkingAccount.transact(200.0);
    assertEquals(0.2, henry.totalInterestEarned(), DOUBLE_DELTA);
  }

  @Test
  public void statement() {
    Account checkingAccount = new Account(Account.Type.CHECKING);
    Account savingsAccount = new Account(Account.Type.SAVINGS);
    Customer henry = new Customer("Henry").openAccount(checkingAccount)
        .openAccount(savingsAccount);

    checkingAccount.transact(100.0);
    savingsAccount.transact(4000.0);
    savingsAccount.transact(-200.0);

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
