package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
  private static final double DOUBLE_DELTA = 1e-15;

 @Test //Test customer statement generation
 public void testApp() {

  Account checkingAccount = new Account(Account.CHECKING);
  Account savingsAccount = new Account(Account.SAVINGS);

  Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

  checkingAccount.deposit(100.0);
  savingsAccount.deposit(4000.0);
  savingsAccount.withdraw(200.0);

  assertEquals("Statement for Henry\n" +
   "\n" +
   "Checking Account\n" +
   "  deposit $100.00\n" +
   "Total $100.00\n" +
   "\n" +
   "Savings Account\n" +
   "  deposit $4,000.00\n" +
   "  withdrawal $200.00\n" +
   "Total $3,800.00\n" +
   "\n" +
   "Total In All Accounts $3,900.00", henry.getStatement());
 }

 @Test
 public void testOneAccountTypes() {
  Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
  assertEquals(1, oscar.getNumberOfAccounts());
 }

 @Test
 public void testTwoAccountTypes() {
  Customer oscar = new Customer("Oscar")
   .openAccount(new Account(Account.SAVINGS));
  oscar.openAccount(new Account(Account.CHECKING));
  assertEquals(2, oscar.getNumberOfAccounts());
 }

 @Test
 public void testThreeAcountTypes() {
  Customer oscar = new Customer("Oscar")
   .openAccount(new Account(Account.SAVINGS));
  oscar.openAccount(new Account(Account.CHECKING));
  oscar.openAccount(new Account(Account.MAXI_SAVINGS));
  assertEquals(3, oscar.getNumberOfAccounts());
 }

 @Test
 public void testTransferWithinAccountsSuccess() {
  Account checkingAccount = new Account(Account.CHECKING);
  checkingAccount.deposit(1000);

  Account savingsAccount = new Account(Account.SAVINGS);
  Customer oscar = new Customer("Oscar")
   .openAccount(checkingAccount);
  oscar.openAccount(savingsAccount);

  oscar.transfer(checkingAccount, savingsAccount, 500);

  assertEquals(500, savingsAccount.sumTransactions(), DOUBLE_DELTA);
 }

 @Test
 public void testTransferWithinAccountsFail() {
  Account checkingAccount = new Account(Account.CHECKING);
  checkingAccount.deposit(250);

  Account savingsAccount = new Account(Account.SAVINGS);
  Customer oscar = new Customer("Oscar")
   .openAccount(checkingAccount);
  try {
   oscar.transfer(checkingAccount, savingsAccount, 250);
  } catch (IllegalArgumentException e) {
   assertEquals(250, checkingAccount.sumTransactions(), DOUBLE_DELTA);
  }
 }
}
