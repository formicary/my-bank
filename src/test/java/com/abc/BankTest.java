package com.abc;

import com.abc.core.Account;
import com.abc.core.AccountType;
import com.abc.core.Bank;
import com.abc.core.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void summaryOfAllCustomers() {
        Bank bank = new Bank();
        Customer jane = new Customer("Jane").openAccount(new Account(AccountType.MAXI_SAVINGS));
        jane.openAccount(new Account(AccountType.SAVINGS));
        bank.addCustomer(jane);
        Customer john = new Customer("John").openAccount(new Account(AccountType.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary" +
                "\n - Jane (2 accounts)" +
                "\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void summaryOfAllCustomersIfBankHasNoCustomers() {
        Bank bank = new Bank();

        assertEquals("Customer Summary", bank.customerSummary());
    }

    @Test
    public void totalInterestPaidByBank() {
        Bank bank = new Bank();
        Customer jane = new Customer("Jane");
        Account janeSavings = new Account(AccountType.SAVINGS);
        jane.openAccount(janeSavings);
        janeSavings.deposit(1500.0);
        Account janeChecking = new Account(AccountType.CHECKING);
        jane.openAccount(janeChecking);
        janeChecking.deposit(500.0);
        bank.addCustomer(jane);
        Customer john = new Customer("John");
        Account johnMaxiSavings = new Account(AccountType.MAXI_SAVINGS);
        john.openAccount(johnMaxiSavings);
        johnMaxiSavings.deposit(2500.0);
        bank.addCustomer(john);

        // TODO: compute interest manually here???
        assertEquals(jane.totalInterestEarned() + john.totalInterestEarned() , bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
