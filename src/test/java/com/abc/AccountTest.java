package com.abc;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void accountStatementTest(){
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry", "000001")
                .openAccount(savingsAccount);

        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Savings Account\n" +
                        "  deposit $4,000.00\n" +
                        "  withdrawal $200.00\n" +
                        "Total $3,800.00"
                , savingsAccount.statementForAccount());
    }

    @Test
    public void checkingAccountInterestTest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill", bank.generateID());
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1500.0);

        assertEquals(1.5, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountBelow1000InterestTest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill", bank.generateID());
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(800);

        assertEquals(0.8, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountAbove1000InterestTest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill", bank.generateID());
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(1200);

        assertEquals(1.4, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsRecentWithdrawalsInterestTest() throws InterruptedException {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill", bank.generateID());
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(400);

        assertEquals(2.6, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsNoRecentWithdrawalsInterestTest() throws InterruptedException {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill", bank.generateID());
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);

        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.JANUARY,15,12,14,0);

        maxiSavingsAccount.lastWithdrawalTime = cal.getTimeInMillis();

        assertEquals(15.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }
}
