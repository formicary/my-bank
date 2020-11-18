package com.abc.customer;

import com.abc.account.Account;
import com.abc.account.AccountType;
import com.abc.account.TransactionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_CustomerHasMultipleAccounts_Expect_CustomerStatementToBeCorrect(){
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0, TransactionType.CUSTOMER_DEPOSIT);
        savingsAccount.deposit(4000.0, TransactionType.CUSTOMER_DEPOSIT);
        savingsAccount.withdraw(200.0);
        savingsAccount.addDailyInterestToAccount();

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $0.02\n" +
                "Total $3,800.02\n" +
                "\n" +
                "Total In All Accounts $3,900.02", henry.getStatement());
    }

    @Test
    public void When_CustomerOpens1Account_Expect_NumberOfAccountsToBe1(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void When_CustomerOpens3Accounts_Expect_NumberOfAccountsToBe3(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void When_CustomerHasMultipleAccounts_Expect_TotalInterestEarnedToBeCorrect() {
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer jane = new Customer("Jane").openAccount(savingsAccount).openAccount(maxiSavingsAccount);
        savingsAccount.deposit(1500.0, TransactionType.CUSTOMER_DEPOSIT);
        savingsAccount.addDailyInterestToAccount();
        maxiSavingsAccount.deposit(2500.0, TransactionType.CUSTOMER_DEPOSIT);
        maxiSavingsAccount.addDailyInterestToAccount();

        assertEquals(0.347945205479452, jane.totalInterestEarned(), DELTA);
    }

    @Test
    public void When_CustomerTransfersBetweenAccounts_Expect_TotalOfBothAccountsToBeCorrect() {
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount).openAccount(checkingAccount);
        checkingAccount.deposit(1500.0, TransactionType.CUSTOMER_DEPOSIT);
        maxiSavingsAccount.deposit(100.0, TransactionType.CUSTOMER_DEPOSIT);

        john.transferAmount(checkingAccount, maxiSavingsAccount, 1000.0);

        assertEquals(500.0, checkingAccount.sumOfTransactions(), DELTA);
        assertEquals(1100.0, maxiSavingsAccount.sumOfTransactions(), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_CustomerTransfersFromNotOwnAccount_Expect_TransferToThrowException() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        checkingAccount.deposit(2000.0, TransactionType.CUSTOMER_DEPOSIT);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);

        john.transferAmount(checkingAccount, savingsAccount, 2000.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_CustomerTransfersToNotOwnAccount_Expect_TransferToThrowException() {
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);
        savingsAccount.deposit(1000.0, TransactionType.CUSTOMER_DEPOSIT);
        Account checkingAccount = new Account(AccountType.CHECKING);

        john.transferAmount(savingsAccount, checkingAccount, 500.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_CustomerTransfersNegativeAmount_Expect_TransferToThrowException() {
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount).openAccount(checkingAccount);
        checkingAccount.deposit(500.0, TransactionType.CUSTOMER_DEPOSIT);

        john.transferAmount(checkingAccount, maxiSavingsAccount, -200.0);
    }

    @Test(expected = IllegalStateException.class)
    public void When_CustomerHasNotEnoughMoneyToTransfer_Expect_TransferToThrowException() {
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount).openAccount(checkingAccount);
        checkingAccount.deposit(500.0, TransactionType.CUSTOMER_DEPOSIT);

        john.transferAmount(checkingAccount, maxiSavingsAccount, 1000.0);
    }
}
