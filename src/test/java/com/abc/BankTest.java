package com.abc;

import com.abc.core.account.Account;
import com.abc.core.account.AccountType;
import com.abc.core.bank.Bank;
import com.abc.core.customer.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void totalInterestPaidOnCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaidOnSavingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaidOnMaxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void summaryOfAllCustomersIsCorrect() {
        Bank bank = new Bank();
        Customer jane = new Customer("Jane").openAccount(new Account(AccountType.MAXI_SAVINGS));
        jane.openAccount(new Account(AccountType.SAVINGS));
        bank.addCustomer(jane);
        Customer john = new Customer("John").openAccount(new Account(AccountType.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary" +
                "\n - Jane (2 accounts)" +
                "\n - John (1 account)", bank.summaryOfAllCustomers());
    }

    @Test
    public void summaryOfAllCustomersIfBankHasNoCustomers() {
        Bank bank = new Bank();
        assertEquals("Customer Summary", bank.summaryOfAllCustomers());
    }

    @Test
    public void totalInterestPaidByBank() {
        Bank bank = new Bank();
        Customer jane = new Customer("Jane");
        bank.addCustomer(jane);
        Account janeSavings = new Account(AccountType.SAVINGS);
        jane.openAccount(janeSavings);
        janeSavings.deposit(1500.0);
        Account janeChecking = new Account(AccountType.CHECKING);
        jane.openAccount(janeChecking);
        janeChecking.deposit(500.0);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        Account johnMaxiSavings = new Account(AccountType.MAXI_SAVINGS);
        john.openAccount(johnMaxiSavings);
        johnMaxiSavings.deposit(2500.0);

        assertEquals(122.5 , bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
