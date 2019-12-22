package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static com.abc.Utils.getDate;
public class BankTest {

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void interestToBePaid() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingAccount = new Account(AccountType.SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(savingAccount);

        bank.addCustomer(customer);

        savingAccount.deposit(1500.0, getDate("22/12/2018"));

        assertEquals(2.0014, bank.totalInterestPaid(getDate("22/12/2019")), Utils.DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(maxAccount);
        bank.addCustomer(customer);

        maxAccount.deposit(3000.0,  getDate("22/12/2018"));

        assertEquals(153.8024, bank.totalInterestPaid( getDate("22/12/2019")), Utils.DOUBLE_DELTA);
    }

    @Test
    public void multipleAccounts(){
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingAccount = new Account(AccountType.SAVINGS);
        Account maxAccount = new Account(AccountType.MAXI_SAVINGS);
        Account maxAccount2 = new Account(AccountType.MAXI_SAVINGS);
        Account savingAccount2 = new Account(AccountType.SAVINGS);

        Customer customer1 = new Customer("Bill");
        customer1.openAccount(checkingAccount);
        bank.addCustomer(customer1);
        checkingAccount.deposit(3000.0,  getDate("22/12/2018"));

        Customer customer2 = new Customer("Bill");
        customer2.openAccount(savingAccount);
        customer2.openAccount(maxAccount);
        bank.addCustomer(customer2);

        savingAccount.deposit(1000, getDate("10/12/2017"));
        savingAccount.withdraw(1000, getDate("10/12/2018"));

        maxAccount.deposit(300, getDate("11/11/2018"));
        maxAccount.withdraw(100, getDate("11/12/2018"));
        maxAccount.deposit(10, getDate("17/12/2018"));

        Customer customer3 = new Customer("Bill");
        customer3.openAccount(maxAccount2);
        customer3.openAccount(savingAccount2);
        bank.addCustomer(customer3);

        maxAccount2.deposit(12345, getDate("20/10/2018"));
        maxAccount2.deposit(145, getDate("29/10/2019"));

        assertEquals(762.8457, bank.totalInterestPaid( getDate("22/12/2019")), Utils.DOUBLE_DELTA);
    }

}
