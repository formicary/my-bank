package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankTest {

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John", new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill", checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(new BigDecimal("100.0"));

        assertEquals("0.10", bank.totalInterestPaid().toString());
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingAccount();
        bank.addCustomer(new Customer("Bill", checkingAccount));

        checkingAccount.deposit(new BigDecimal("1500.0"));

        assertEquals("3.00", bank.totalInterestPaid().toString());
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiAccount = new MaxiAccount();
        bank.addCustomer(new Customer("Bill", maxiAccount));

        maxiAccount.deposit(new BigDecimal("3000.0"));

        assertEquals("300.00", bank.totalInterestPaid().toString());
    }

    @Test
    public void multiCustomerInterestPaid() {
        Bank bank = new Bank();
        Account bob_checking = new CheckingAccount();
        Account bob_saving = new SavingAccount();
        Account charlie_checking = new CheckingAccount();
        Account charlie_saving = new SavingAccount();
        Customer bob = new Customer("Bob", bob_checking);
        bob.openAccount(bob_saving);
        Customer charlie = new Customer("Charlie", charlie_checking);
        charlie.openAccount(charlie_saving);

        bob_checking.deposit(new BigDecimal("400.00"));
        bob_saving.deposit(new BigDecimal("9000.00"));
        charlie_checking.deposit(Transaction.TWO_HUNDRED);
        charlie_saving.deposit(new BigDecimal("6000.00"));
        bank.addCustomer(bob);
        bank.addCustomer(charlie);

        //bob total interest earned => 18.40
        //charlie total interest earned => 12.20
        assertEquals("30.60", bank.totalInterestPaid().toString());
    }

}
