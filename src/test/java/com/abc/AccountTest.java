package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill", "bill@email.com").addAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(10000.0);
        checkingAccount.accrueInterest();
        // Testing daily interest accrual
        assertEquals(0.027397260273972, checkingAccount.getInterestAccrued(), DOUBLE_DELTA);
        //testing compound interest
        for(int i = 0; i < 365; i++){
            checkingAccount.accrueInterest();
        }
        assertEquals(10.0049879, checkingAccount.getInterestAccrued(), DOUBLE_DELTA); //todo BIGDECIMAL
    }
    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill", "bill@email.com").addAccount(savingsAccount));
        savingsAccount.deposit(1500.0);
        savingsAccount.accrueInterest();
        assertEquals(2.0, savingsAccount.getInterestAccrued(), DOUBLE_DELTA);
        savingsAccount.accrueInterest();
        assertEquals(4.004, savingsAccount.getInterestAccrued(), DOUBLE_DELTA);
    }
    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill", "bill@email.com").addAccount(maxiSavingsAccount));

        // Testing interest accrual.
        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.accrueInterest();
        assertEquals(150, maxiSavingsAccount.getInterestAccrued(), DOUBLE_DELTA);

        //Testing interest toggling depending on days since last withdrawal.
        maxiSavingsAccount.withdraw(1000); // Withdrawal made at present time.
        LocalDate tenDaysAhead = DateProvider.now().plusDays(10);
        LocalDate elevenDaysAhead = DateProvider.now().plusDays(11);
        assertEquals(0.05, maxiSavingsAccount.getCurrentYearlyInterest(), DOUBLE_DELTA);
        maxiSavingsAccount.toggleInterestRate(tenDaysAhead); //interest rate is adjusted as if it were 10 days in the future - in effect, withdrawal was made within last 10 days.
        assertEquals(0.001, maxiSavingsAccount.getCurrentYearlyInterest(), DOUBLE_DELTA);
        maxiSavingsAccount.toggleInterestRate(elevenDaysAhead); //Interest rate adjusted as if withdrawal were made 11 days ago - outside the 10 day limit for low interest rates.
        assertEquals(0.05, maxiSavingsAccount.getCurrentYearlyInterest(), DOUBLE_DELTA);
    }
}
