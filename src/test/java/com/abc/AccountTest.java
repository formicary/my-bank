package com.abc;

import com.abc.ENUMS.AccountType;
import com.abc.ENUMS.TransactionType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;


    Customer oscar;
    Account checkingAccount;
    Account savingAccount;
    Account maxiSavingAccount;
    Bank bank;
    @Before
    public void init()
    {
        oscar=new Customer("Oscar");
        checkingAccount=new Account(AccountType.CHECKING);
        savingAccount=new Account(AccountType.SAVINGS);
        maxiSavingAccount=new Account(AccountType.MAXI_SAVINGS);
        bank=new Bank();

    }
    @Test
    public void depositFundsTest(){
        oscar.openAccount(checkingAccount);
        checkingAccount.deposit(200.00,TransactionType.DEPOSIT);
        assertEquals(200.00, checkingAccount.getBalance(),DOUBLE_DELTA);
    }
    @Test(expected = IllegalArgumentException.class)
    public void depositFundsIfNegativeAmountTest(){
       oscar.openAccount(checkingAccount);
        checkingAccount.deposit(-200.00,TransactionType.DEPOSIT);

    }
    @Test(expected = IllegalArgumentException.class)
    public void depositFundsIfZeroAmountTest(){
        oscar.openAccount(checkingAccount);
        checkingAccount.deposit(0.00,TransactionType.DEPOSIT);

    }
    @Test
    public void withDrawFundsTest(){
      oscar.openAccount(checkingAccount);
        checkingAccount.deposit(500.00,TransactionType.DEPOSIT);
        checkingAccount.withdraw(200.00, TransactionType.WITHDRAWL);
        assertEquals(300.00, checkingAccount.getBalance(),DOUBLE_DELTA);
    }
    @Test(expected = IllegalStateException.class)
    public void withDrawFundsIfInsufficientBalanceTest(){
       oscar.openAccount(checkingAccount);
        checkingAccount.deposit(100.00,TransactionType.DEPOSIT);
        checkingAccount.withdraw(200.00,TransactionType.WITHDRAWL);
    }
    @Test(expected = IllegalArgumentException.class)
    public void withDrawFundsIfNegativeWithdrawAmountTest(){
       oscar.openAccount(checkingAccount);
        checkingAccount.deposit(500.00,TransactionType.DEPOSIT);
        checkingAccount.withdraw(-200.00,TransactionType.WITHDRAWL);


    }
    @Test(expected = IllegalArgumentException.class)
    public void withDrawFundsIfZeroWithdrawAmountTest(){
       oscar.openAccount(checkingAccount);
        checkingAccount.deposit(500.00,TransactionType.DEPOSIT);
        checkingAccount.withdraw(0.00,TransactionType.WITHDRAWL);


    }

    @Test
    public void checkingAccountInterestTest() {
        oscar.openAccount(checkingAccount);
        bank.addCustomer(oscar);
        checkingAccount.deposit(100.0,TransactionType.DEPOSIT);
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertEquals(100.00, checkingAccount.getBalance(), DOUBLE_DELTA);

    }


    @Test
    public void savingsAccountInterestIfAmountGreaterThan1000Test() {
        bank.addCustomer(oscar.openAccount(savingAccount));
        savingAccount.deposit(1500.0,TransactionType.DEPOSIT);
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void savingsAccountInterestIfAmountLessThanOrEqualTo1000Test() {
        bank.addCustomer(oscar.openAccount(savingAccount));
        savingAccount.deposit(500.0,TransactionType.DEPOSIT);
        assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestIfNoWithDrawalInLast10DaysTest() {

        bank.addCustomer(oscar.openAccount(maxiSavingAccount));
        maxiSavingAccount.deposit(3000.0,TransactionType.DEPOSIT);
        maxiSavingAccount.deposit(1000.0,TransactionType.DEPOSIT);
        assertEquals(200.0, maxiSavingAccount.interestEarned(), DOUBLE_DELTA);
    }
    @Test
    public void maxiSavingsAccountInterestWithWithDrawalInLast10DaysTest() {

        bank.addCustomer(oscar.openAccount(maxiSavingAccount));
        maxiSavingAccount.deposit(3000.0,TransactionType.DEPOSIT);
        maxiSavingAccount.withdraw(1000.0,TransactionType.WITHDRAWL);
        assertEquals(2.0, maxiSavingAccount.interestEarned(), DOUBLE_DELTA);
    }

}
