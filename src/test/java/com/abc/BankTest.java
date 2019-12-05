package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

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

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        //test no recent withdrawal
        maxiAccount.deposit(3000.0);
        assertEquals(150.0, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);

        maxiAccount.withdraw(3000);
        assertEquals(0, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);

        //test recent withdrawal
        maxiAccount.deposit(1000);
        assertEquals(1, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void testCompoundInterest() {
        Calendar calendar = Calendar.getInstance();
        //test compund interest against formula
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000);
        Transaction transaction = checkingAccount.transactions.get(0);
        calendar.add(Calendar.DATE, -10);
        transaction.transactionDate = calendar.getTime();
        BigDecimal thousand = new BigDecimal(1000);
        BigDecimal estimate = new BigDecimal(1 + 0.001).pow(11).multiply(thousand).subtract(thousand);
        assertEquals(estimate.doubleValue(),checkingAccount.interestEarned().doubleValue(),1E-10);
    }

}
