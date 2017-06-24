package com.abc;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.SavingsAccount;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

  private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

  @Test
  public void getStatementWithMultipleAccountsAndTransactions() {
    final Account checkingAccount = new CheckingAccount();
    final Account savingsAccount = new SavingsAccount();
    final Customer customer = new Customer("Henry");
    customer.openAccount("one", checkingAccount);
    customer.openAccount("two", savingsAccount);
    checkingAccount.deposit(ONE_HUNDRED);
    savingsAccount.deposit(ONE_HUNDRED);
    savingsAccount.withdraw(ONE_HUNDRED);
    assertEquals(
        "Statement for Henry\n"
            + "\n"
            + "Checking Account\n"
            + "  deposit $100.00\n"
            + "Total $100.00\n"
            + "\n"
            + "Savings Account\n"
            + "  deposit $100.00\n"
            + "  withdrawal $100.00\n"
            + "Total $0.00\n"
            + "\n"
            + "Total In All Accounts $100.00",
        customer.getStatement());
  }

  @Test
  public void getStatementWithMultipleAccountsNoTransactions() {
    final Account checkingAccount = new CheckingAccount();
    final Account savingsAccount = new SavingsAccount();
    final Customer customer = new Customer("Henry");
    customer.openAccount("one", checkingAccount);
    customer.openAccount("two", savingsAccount);
    assertEquals(
        "Statement for Henry\n"
            + "\n"
            + "Checking Account\n"
            + "Total $0.00\n"
            + "\n"
            + "Savings Account\n"
            + "Total $0.00\n"
            + "\n"
            + "Total In All Accounts $0.00",
        customer.getStatement());
  }

  @Test
  public void customerWithOneAccount() {
    final Customer customer = new Customer("Oscar");
    customer.openAccount("id", new SavingsAccount());
    assertEquals(1, customer.getNumberOfAccounts());
  }

  @Test
  public void customerWithMultipleAccounts() {
    final Customer customer = new Customer("Oscar");
    customer.openAccount("one", new SavingsAccount());
    customer.openAccount("two", new CheckingAccount());
    customer.openAccount("three", new CheckingAccount());
    assertEquals(3, customer.getNumberOfAccounts());
  }

  @Test
  public void depositToAccountIncreasesCorrectAccount() {
    final Customer customer = new Customer("customer");
    final Account account = new CheckingAccount();
    customer.openAccount("one", new CheckingAccount());
    customer.openAccount("two", account);
    customer.openAccount("three", new CheckingAccount());
    customer.depositToAccount("two", ONE_HUNDRED);
    assertEquals(ONE_HUNDRED, account.getTransactionsSum());
  }

  @Test
  public void withdrawFromAccountIncreasesCorrectAccount() {
    final Customer customer = new Customer("customer");
    final Account account = new CheckingAccount();
    customer.openAccount("one", new CheckingAccount());
    customer.openAccount("two", account);
    customer.openAccount("three", new CheckingAccount());
    customer.withdrawFromAccount("two", ONE_HUNDRED);
    assertEquals(ONE_HUNDRED.negate(), account.getTransactionsSum());
  }

  @Test
  public void transferToIncreasesToAccountsAmount() {
    final Customer customer = new Customer("customer");
    final Account from = new CheckingAccount();
    final Account to = new CheckingAccount();
    customer.openAccount("one", from);
    customer.openAccount("two", to);
    customer.transferBetweenAccounts("one", "two", ONE_HUNDRED);
    assertEquals(ONE_HUNDRED, to.getTransactionsSum());
  }

  @Test
  public void transferToDecreasesFromAccountsAmount() {
    final Customer customer = new Customer("customer");
    final Account from = new CheckingAccount();
    final Account to = new CheckingAccount();
    customer.openAccount("one", from);
    customer.openAccount("two", to);
    customer.transferBetweenAccounts("one", "two", ONE_HUNDRED);
    assertEquals(ONE_HUNDRED.negate(), from.getTransactionsSum());
  }
}
