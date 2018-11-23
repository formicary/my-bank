package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John", new Account(AccountTypes.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountTypes.CHECKING);
        Customer bill = new Customer("Bill", checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals("0.1", bank.totalInterestPaid().stripTrailingZeros().toPlainString());
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountTypes.SAVINGS);
        bank.addCustomer(new Customer("Bill", checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals("2", bank.totalInterestPaid().stripTrailingZeros().toPlainString());
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountTypes.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill", checkingAccount));
        Transaction.testing = true;
        Transaction.daysfromNow = -90;
        checkingAccount.deposit(BigDecimal.valueOf(2000.0));
        System.out.println("a "+bank.totalInterestPaid().setScale(2,BigDecimal.ROUND_HALF_EVEN));
        Transaction.testing = true;
        Transaction.daysfromNow = -60;
        checkingAccount.withdraw(BigDecimal.valueOf(50.0));
        Transaction.testing = true;
        Transaction.daysfromNow = -30;
        checkingAccount.withdraw(BigDecimal.valueOf(50.0));
        assertEquals("16.09", bank.totalInterestPaid().setScale(2,BigDecimal.ROUND_HALF_EVEN).toPlainString());
    }

}
