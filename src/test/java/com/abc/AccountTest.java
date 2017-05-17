package com.abc;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
/**
 * Created by User on 17.05.2017.
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void accountSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        Account savingsAccount = new Account(Account.SAVINGS);

        john.openAccount(savingsAccount);
        john.openAccount(maxiAccount);

        assertEquals(2, john.getNumberOfAccounts());
    }

    @Test
    public void interestRate() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);

        john.openAccount(checkingAccount);
        john.openAccount(maxiAccount);

        maxiAccount.deposit(2000);
        checkingAccount.deposit(2000);

        double expectedvalue = (2000*Account.maxiSavingsInterestRateFewDays/Account.numberOfDays) + (2000*Account.checkingInterestRate/Account.numberOfDays);
        assertEquals(expectedvalue, john.totalInterestEarned(),DOUBLE_DELTA);
    }
}
