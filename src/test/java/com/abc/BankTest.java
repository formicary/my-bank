package com.abc;

import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

public class BankTest {

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0, LocalDate.of(2019,1,1));
        assertEquals("0,10", String.format("%.2f", bank.totalInterestPaid(LocalDate.of(2020,1,1))));
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(100.0, LocalDate.of(2019,1,1));

        assertEquals("0,10", String.format("%.2f", bank.totalInterestPaid(LocalDate.of(2020,1,1))));
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0, LocalDate.of(2019,1,1));

        assertEquals("145,47", String.format("%.2f", bank.totalInterestPaid(LocalDate.of(2020,1,1))));
    }

}
