package com.abc;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void canCreateDifferentAccounts() {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();
        Account maxiSaving = new MaxiSavingsAccount();
        assertEquals(Account.Accounts.CHECKING, checking.getAccountType());
        assertEquals(Account.Accounts.SAVINGS, saving.getAccountType());
        assertEquals(Account.Accounts.MAXI_SAVINGS, maxiSaving.getAccountType());
    }

    @Test
    public void canAddMultipleTransactions() throws InterruptedException {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();
        Account maxiSaving = new MaxiSavingsAccount();

        Transaction t1 = new Transaction(10, DateProvider.getInstance().now());
        TimeUnit.MILLISECONDS.sleep(5);
        Transaction t2 = new Transaction(20, DateProvider.getInstance().now());
        TimeUnit.MILLISECONDS.sleep(5);
        Transaction t3 = new Transaction(30, DateProvider.getInstance().now());
        TimeUnit.MILLISECONDS.sleep(5);

        checking.transactions.add(t1); checking.transactions.add(t2); checking.transactions.add(t3);
        saving.transactions.add(t1); saving.transactions.add(t2); saving.transactions.add(t3);
        maxiSaving.transactions.add(t1); maxiSaving.transactions.add(t2); maxiSaving.transactions.add(t3);

        assertTrue(checking.transactions.size() == 3);
        assertTrue(saving.transactions.size() == 3);
        assertTrue(maxiSaving.transactions.size() == 3);
    }

    @Test
    public void accountTransactionsAreOrdered() throws InterruptedException {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();
        Account maxiSaving = new MaxiSavingsAccount();

        Transaction t1 = new Transaction(10, DateProvider.getInstance().now());
        TimeUnit.MILLISECONDS.sleep(5);
        Transaction t2 = new Transaction(20, DateProvider.getInstance().now());
        TimeUnit.MILLISECONDS.sleep(5);
        Transaction t3 = new Transaction(30, DateProvider.getInstance().now());
        TimeUnit.MILLISECONDS.sleep(5);

        checking.transactions.add(t1); checking.transactions.add(t2); checking.transactions.add(t3);
        saving.transactions.add(t1); saving.transactions.add(t2); saving.transactions.add(t3);
        maxiSaving.transactions.add(t1); maxiSaving.transactions.add(t2); maxiSaving.transactions.add(t3);

        assertTrue(checking.transactions.get(0).getAmount() == 10 &&
                           checking.transactions.get(1).getAmount() == 20 &&
                           checking.transactions.get(2).getAmount() == 30);

        long time1 = checking.transactions.get(0).getTransactionDate().getTime();
        long time2 = checking.transactions.get(1).getTransactionDate().getTime();
        long time3 = checking.transactions.get(2).getTransactionDate().getTime();

        assertTrue(time1 < time2 &&  time2 < time3);

        assertTrue(saving.transactions.get(0).getAmount() == 10 &&
                           saving.transactions.get(1).getAmount() == 20 &&
                           saving.transactions.get(2).getAmount() == 30);

        time1 = saving.transactions.get(0).getTransactionDate().getTime();
        time2 = saving.transactions.get(1).getTransactionDate().getTime();
        time3 = saving.transactions.get(2).getTransactionDate().getTime();

        assertTrue(time1 < time2 &&  time2 < time3);

        assertTrue(maxiSaving.transactions.get(0).getAmount() == 10 &&
                           maxiSaving.transactions.get(1).getAmount() == 20 &&
                           maxiSaving.transactions.get(2).getAmount() == 30);

        time1 = maxiSaving.transactions.get(0).getTransactionDate().getTime();
        time2 = maxiSaving.transactions.get(1).getTransactionDate().getTime();
        time3 = maxiSaving.transactions.get(2).getTransactionDate().getTime();

        assertTrue(time1 < time2 &&  time2 < time3);
    }

    @Test
    public void canDeposit() {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();
        Account maxiSaving = new MaxiSavingsAccount();

        checking.deposit(5); saving.deposit(10); maxiSaving.deposit(15);
        assertEquals(5, checking.sumTransactions(), DOUBLE_DELTA);
        assertEquals(10, saving.sumTransactions(), DOUBLE_DELTA);
        assertEquals(15, maxiSaving.sumTransactions(), DOUBLE_DELTA);
    }

    @Test (expected = Exception.class)
    public void cantDeposit() {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();
        Account maxiSaving = new MaxiSavingsAccount();

        checking.deposit(-5); saving.deposit(-10); maxiSaving.deposit(-15);
        fail();
    }

    @Test
    public void canWithdraw() {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();
        Account maxiSaving = new MaxiSavingsAccount();

        checking.deposit(5); saving.deposit(10); maxiSaving.deposit(15);
        checking.withdraw(5); saving.withdraw(10); maxiSaving.withdraw(15);
        assertEquals(0, checking.sumTransactions(), DOUBLE_DELTA);
        assertEquals(0, saving.sumTransactions(), DOUBLE_DELTA);
        assertEquals(0, maxiSaving.sumTransactions(), DOUBLE_DELTA);
    }

    @Test (expected = Exception.class)
    public void cantWithdraw() {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();
        Account maxiSaving = new MaxiSavingsAccount();

        checking.deposit(5); saving.deposit(10); maxiSaving.deposit(15);
        checking.withdraw(6); saving.withdraw(11); maxiSaving.withdraw(16);
        fail();
    }

    @Test
    public void canTransfer() {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();

        checking.deposit(10);
        Account.transfer(checking, saving, 5);

        assertEquals(5, checking.sumTransactions(), DOUBLE_DELTA);
        assertEquals(5, saving.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = Exception.class)
    public void cantTransfer() {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();

        checking.deposit(10);
        Account.transfer(checking, saving, 15);
        fail();
    }

    @Test
    public void canEarnInterest() {
        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();
        Account maxiSaving = new MaxiSavingsAccount();

        checking.deposit(10); saving.deposit(10); maxiSaving.deposit(10);

        assertEquals(10 * 0.001, checking.interestEarned(), DOUBLE_DELTA);
        assertEquals(10 * 0.001, saving.interestEarned(), DOUBLE_DELTA);

        saving.deposit(1000);
        assertEquals((1000 * 0.001) + (10 * 0.002), saving.interestEarned(), DOUBLE_DELTA);

        maxiSaving.deposit(100);
        assertEquals(110 * 0.05, maxiSaving.interestEarned(), DOUBLE_DELTA);

        long MILLISECONDS_PER_DAY = 86400000;
        Date d = new Date (DateProvider.getInstance().now().getTime() - (13 * MILLISECONDS_PER_DAY));

        maxiSaving.transactions.add(new Transaction(-20, d));
        assertEquals(90 * 0.05, maxiSaving.interestEarned(), DOUBLE_DELTA);

        maxiSaving.withdraw(10);
        assertEquals(80 * 0.001, maxiSaving.interestEarned(), DOUBLE_DELTA);
    }
}
