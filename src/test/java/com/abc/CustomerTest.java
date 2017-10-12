package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {

  @Test //Test customer statement generation
  public void testApp() {
    Account checkingAccount = new CheckingAccount();
    Account savingsAccount = new SavingsAccount();

    checkingAccount.deposit(100.0);
    savingsAccount.deposit(4000.0);
    savingsAccount.withdraw(200.0);
    
    Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
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

  @Test
  public void testOneAccount() {
    Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
    assertEquals(1, oscar.getNumberOfAccounts());
  }

  @Test
  public void testTwoAccount() {
    Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
    oscar.openAccount(new CheckingAccount());
    assertEquals(2, oscar.getNumberOfAccounts());
  }

  @Test
  public void testThreeAcounts() {
    Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
    oscar.openAccount(new CheckingAccount());
    oscar.openAccount(new MaxiSavingsAccount());
    assertEquals(3, oscar.getNumberOfAccounts());
  }
  
  @Test
  public void transferFromEmptyTest() {
    Account checkingAccount = new CheckingAccount();
    Account savingsAccount = new SavingsAccount();
    checkingAccount.deposit(100.0);
    
    Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
    henry.transferMoney(savingsAccount, checkingAccount, 50.0);

    assertEquals(savingsAccount.sumTransactions(), -50.0, 0.01);
    assertEquals(checkingAccount.sumTransactions(), 150.0, 0.01);
  }
}
