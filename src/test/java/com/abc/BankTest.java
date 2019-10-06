package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class BankTest {
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John", "john@email.com");
        john.addAccount(new CheckingAccount());
        bank.addCustomer(john);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
        john.addAccount(new SavingsAccount());
        // testing if number of accounts is correctly pluralised - increases coverage.
        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void totalInterest(){//TODO refactor with bigDecimal
        Bank bank = new Bank();
        Customer john = new Customer("John", "john@email.com");
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        john.addAccount(maxiSavingsAccount);
        maxiSavingsAccount.deposit(BigDecimal.valueOf(1000));
        maxiSavingsAccount.accrueInterest();

        Customer chris = new Customer("Chris", "chris@email.com");

        Account checkingAccount = new CheckingAccount();
        chris.addAccount(checkingAccount);
        checkingAccount.deposit(BigDecimal.valueOf(1000));
        checkingAccount.accrueInterest();
        bank.addCustomer(john);
        bank.addCustomer(chris);
        BigDecimal expectedTotalInterest = BigDecimal.valueOf(0.139726027397260);
        BigDecimal actualValue15DP = bank.totalInterestPaid().setScale(15, RoundingMode.HALF_UP);
        assertEquals(0, expectedTotalInterest.compareTo(actualValue15DP));
    }

}
