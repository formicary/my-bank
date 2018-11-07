package com.abc;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;
import com.abc.Account.MaxiSavingsAccount;
import com.abc.Account.SavingsAccount;
import com.abc.Exception.IllegalTransferException;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void statementTest() {

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.toString());
    }

    @Test
    public void oneAccountTest() {
        Account account = new MaxiSavingsAccount();
        Customer oscar = new Customer("Oscar").openAccount(account);
        account.deposit(100.0);
        assertEquals(100.0, oscar.getTotalBalance(), DOUBLE_DELTA);
        account.withdraw(40.0);
        assertEquals(60.0, oscar.getTotalBalance(), DOUBLE_DELTA);
    }

    @Test
    public void multipleAccountsTest() {
        Account account = new SavingsAccount();
        Customer oscar = new Customer("Oscar").openAccount(account);
        assertEquals(1, oscar.getNumberOfAccounts());
        Random rand = new Random();
        double amount, withdraw, deposit = 1000.0, total = deposit;
        account.deposit(deposit);

        for (int i = 1; i < 500; i++) {
            amount = oscar.getAccount(i - 1).getTransaction(0).amount;
            assertEquals(deposit, amount, DOUBLE_DELTA);
            withdraw = rand.nextInt(999)+1;
            oscar.getAccount(i - 1).withdraw(withdraw);
            total -= withdraw;
            int accountType = rand.nextInt(3);
            switch (accountType){
                default:
                    account = new CheckingAccount();
                    break;
                case 1:
                    account = new SavingsAccount();
                    break;
                case 2:
                    account = new MaxiSavingsAccount();
                    break;
            }
            oscar.openAccount(account);
            deposit = rand.nextInt(20000) + 1000;
            total += deposit;
            account.deposit(deposit);
            assertEquals(i + 1, oscar.getNumberOfAccounts());
        }
        assertEquals(total, oscar.getTotalBalance(), DOUBLE_DELTA);
    }

    @Test
    public void transferBetweenAccountsTest() {
        SavingsAccount savings = new SavingsAccount();
        CheckingAccount checking = new CheckingAccount();
        Customer oscar = new Customer("Oscar")
                .openAccount(savings)
                .openAccount(checking);
        savings.deposit(2000);
        assertEquals(savings.getBalance(), 2000, DOUBLE_DELTA);
        oscar.transferBetweenAccounts(1500, 0, 1);
        assertEquals(savings.getBalance(), 500, DOUBLE_DELTA);
        assertEquals(checking.getBalance(), 1500, DOUBLE_DELTA);

        boolean exists1 = false, exists2 = false, amount = false, balance = false;
        try {
            oscar.transferBetweenAccounts(10, 5, 1);
        } catch (IllegalTransferException e) {
            assertEquals("account must exist in customer's list", e.getMessage());
            exists1 = true;
        }
        try {
            oscar.transferBetweenAccounts(0, 0, 10);
        } catch (IllegalTransferException e) {
            assertEquals("account must exist in customer's list", e.getMessage());
            exists2 = true;
        }
        try {
            oscar.transferBetweenAccounts(0, 0, 1);
        } catch (IllegalTransferException e) {
            assertEquals("amount must be greater than zero", e.getMessage());
            amount = true;
        }
        try {
            oscar.transferBetweenAccounts(501, 0, 1);
        } catch (IllegalTransferException e) {
            assertEquals("account cannot contain less than transaction amount", e.getMessage());
            balance = true;
        }
        assertTrue(exists1);
        assertTrue(exists2);
        assertTrue(amount);
        assertTrue(balance);

        oscar.transferBetweenAccounts(500, 0, 1);
        assertEquals(savings.getBalance(), 0, DOUBLE_DELTA);
        assertEquals(checking.getBalance(), 2000, DOUBLE_DELTA);

        oscar.transferBetweenAccounts(2000, 1, 0);
        assertEquals(savings.getBalance(), 2000, DOUBLE_DELTA);
        assertEquals(checking.getBalance(), 0, DOUBLE_DELTA);
    }

    @Test
    public void getNameTest() {
        Customer oscar = new Customer("Oscar");
        assertEquals("Oscar", oscar.getName());
    }

    @Test
    public void openAccountTest() {
        Account account = new SavingsAccount();
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(account);
        assertEquals(account, oscar.getAccount(0));
    }

    @Test
    public void totalInterestEarnedTest() {
        Account checking = new CheckingAccount();
        Account savings = new SavingsAccount();
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(checking);
        oscar.openAccount(savings);
        checking.deposit(3000); // 3.0 per annum
        savings.deposit(3000); // 5.0 per annum
        oscar.accrueInterestForAllAccounts();

        Transaction checkT = checking.getLastTransaction();
        Transaction checkS = savings.getLastTransaction();
        assertEquals(3.0/365.0, checkT.amount, DOUBLE_DELTA);
        assertEquals(5.0/365.0, checkS.amount, DOUBLE_DELTA);
        assertEquals(8.0/365.0, oscar.totalInterestEarned(), DOUBLE_DELTA);

    }

    @Test
    public void getTotalBalanceTest() {
        Account checking = new CheckingAccount();
        Account savings = new SavingsAccount();
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(checking);
        oscar.openAccount(savings);
        checking.deposit(877);
        savings.deposit(123);

        Transaction checkT = checking.getLastTransaction();
        Transaction checkS = savings.getLastTransaction();
        assertEquals(1000.0, oscar.getTotalBalance(), DOUBLE_DELTA);

    }

    @Test
    public void getAccountTestTest(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new CheckingAccount())
                .openAccount(new SavingsAccount())
                .openAccount(new MaxiSavingsAccount());
        assertEquals(Account.AccountType.SAVINGS,oscar.getAccount(1).getAccountType());
    }

    @Test
    public void getTotalAccountsTest(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new CheckingAccount())
                .openAccount(new SavingsAccount())
                .openAccount(new MaxiSavingsAccount());
        assertEquals(3,oscar.getNumberOfAccounts());
    }
}
