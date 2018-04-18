package com.abc.Accounts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Tests for the AccountManager class.
 */
@RunWith(PowerMockRunner.class)
public class AccountManagerTests {
    /**
     * The dummy account ID.
     */
    private final int dummyAccountId = 1;

    /**
     * The dummy other account ID.
     */
    private final int dummyOtherAccountId = 2;

    /**
     * The dummy customer ID.
     */
    private final int dummyCustomerId = 3;

    /**
     * The dummy other customer ID.
     */
    private final int dummyOtherCustomerId = 4;

    /**
     * The dummy amount of money.
     */
    private final double dummyAmount = 20.0;

    /**
     * The mock account ID manager.
     */
    @Mock
    private IAccountIdManager mockAccountIdManager;

    /**
     * The mock account factory.
     */
    @Mock
    private IAccountFactory mockAccountFactory;

    /**
     * The account manager.
     */
    private AccountManager accountManager;

    /**
     * Sets up before each test.
     */
    @Before
    public void setUp() {
        this.mockAccountIdManager = Mockito.mock(IAccountIdManager.class);
        this.mockAccountFactory = Mockito.mock(IAccountFactory.class);

        when(this.mockAccountIdManager.generateAccountId()).thenReturn(this.dummyAccountId);

        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId)).thenReturn(null);
        when(this.mockAccountFactory.createSavingsAccount(this.dummyAccountId, this.dummyCustomerId)).thenReturn(null);
        when(this.mockAccountFactory.createMaxiSavingsAccount(this.dummyAccountId, this.dummyCustomerId)).thenReturn(null);

        this.accountManager = new AccountManager(this.mockAccountIdManager, this.mockAccountFactory);
    }


    /**
     * Opening checking account creates checking account on account factory.
     */
    @Test
    public void openingCheckingAccountCreatesCheckingAccountOnFactory() {
        int accountId = this.accountManager.openAccount(dummyCustomerId, AccountType.CHECKING);

        Mockito.verify(this.mockAccountFactory).createCheckingAccount(this.dummyAccountId, this.dummyCustomerId);

        Assert.assertEquals(accountId, this.dummyAccountId);
    }

    /**
     * Opening savings account creates savings account on account factory.
     */
    @Test
    public void openingSavingsAccountCreatesSavingsAccountOnFactory() {
        int accountId = this.accountManager.openAccount(dummyCustomerId, AccountType.SAVINGS);

        Mockito.verify(this.mockAccountFactory).createSavingsAccount(this.dummyAccountId, this.dummyCustomerId);

        Assert.assertEquals(accountId, this.dummyAccountId);
    }

    /**
     * Opening maxi-savings account creates maxi-savings account on account factory.
     */
    @Test
    public void openingMaxiSavingsAccountCreatesMaxiSavingsAccountOnFactory() {
        int accountId = this.accountManager.openAccount(dummyCustomerId, AccountType.MAXI_SAVINGS);

        Mockito.verify(this.mockAccountFactory).createMaxiSavingsAccount(this.dummyAccountId, this.dummyCustomerId);

        Assert.assertEquals(accountId, this.dummyAccountId);;
    }

    /**
     * Opening an unknown type of account throws account exception. expected = AccountException.class
     */
    @Test(expected = AccountException.class)
    @PrepareForTest(AccountType.class)
    public void openingUnknownAccountThrowsAccountException() {
        AccountType mockAccountType = PowerMockito.mock(AccountType.class);
        AccountType[] accountTypes = AccountType.values();

        PowerMockito.mockStatic(AccountType.class);

        Whitebox.setInternalState(mockAccountType, "name", "UNKNOWN_TYPE");
        Whitebox.setInternalState(mockAccountType, "ordinal", accountTypes.length);

        when(AccountType.values())
                .thenReturn(new AccountType[] {AccountType.CHECKING, AccountType.SAVINGS, AccountType.MAXI_SAVINGS, mockAccountType});

        this.accountManager.openAccount(this.dummyCustomerId, mockAccountType);
    }

    /**
     * Getting number of accounts returns correct number of accounts.
     */
    @Test
    public void gettingNumberOfAccountsReturnsCorrectValue() {
        this.accountManager.openAccount(this.dummyCustomerId, AccountType.SAVINGS);
        this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        int numOfAccounts = this.accountManager.getNumberOfAccounts();

        Assert.assertEquals(numOfAccounts, 2);
    }

    /**
     * Getting accounts for a customer gets the accounts.
     */
    @Test
    public void gettingAccountsForCustomerGetsAccounts() {
        CheckingAccount mockCheckingAccount = Mockito.mock(CheckingAccount.class);
        when(mockCheckingAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockCheckingAccount.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockCheckingAccount);

        this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        SavingsAccount mockSavingsAccount = Mockito.mock(SavingsAccount.class);
        when(mockSavingsAccount.getAccountId()).thenReturn(this.dummyOtherAccountId);
        when(mockSavingsAccount.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockAccountIdManager.generateAccountId()).thenReturn(this.dummyOtherAccountId);
        when(this.mockAccountFactory.createSavingsAccount(this.dummyOtherAccountId, this.dummyCustomerId))
                .thenReturn(mockSavingsAccount);

        this.accountManager.openAccount(this.dummyCustomerId, AccountType.SAVINGS);

        List<IAccount> accounts = this.accountManager.getAccounts(this.dummyCustomerId);

        IAccount firstAccount = accounts.get(0);
        IAccount secondAccount = accounts.get(1);

        Mockito.verify(mockCheckingAccount).getCustomerId();
        Mockito.verify(mockSavingsAccount).getCustomerId();

        Assert.assertEquals(firstAccount.getAccountId(), this.dummyAccountId);
        Assert.assertEquals(secondAccount.getAccountId(), this.dummyOtherAccountId);
    }

    /**
     * Getting account gets the account.
     */
    @Test
    public void gettingAccountGetsAccount() {
        CheckingAccount mockAccount = Mockito.mock(CheckingAccount.class);
        when(mockAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockAccount.getCustomerId()).thenReturn(this.dummyCustomerId);

        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockAccount);

        int accountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        IAccount account = this.accountManager.getAccount(accountId);

        Mockito.verify(mockAccount).getAccountId();

        Assert.assertEquals(account.getAccountId(), accountId);
    }

    /**
     * Getting account when no account exists returns null.
     */
    @Test
    public void gettingAccountWhenNoAccountExistsReturnsNull() {
        IAccount account = this.accountManager.getAccount(this.dummyAccountId);

        Assert.assertNull(account);
    }

    /**
     * Withdrawing from an account withdraws from the account.
     */
    @Test
    public void withdrawingFromAccountWithdrawsFromAccount() {
        CheckingAccount mockAccount = Mockito.mock(CheckingAccount.class);
        when(mockAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockAccount.getCustomerId()).thenReturn(this.dummyCustomerId);

        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockAccount);

        int accountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        this.accountManager.withdraw(this.dummyCustomerId, accountId, this.dummyAmount);

        Mockito.verify(mockAccount).withdraw(this.dummyAmount);
    }

    /**
     * Withdrawing from account when no such account exists throws an AccountException.
     */
    @Test(expected = AccountException.class)
    public void withdrawingFromAccountWhenNoAccountExistsThrowsAccountException() {
        this.accountManager.withdraw(this.dummyCustomerId, this.dummyAccountId, this.dummyAmount);
    }

    /**
     * Withdrawing from account when the given customer ID doesn't match the account's customer ID
     * throws an AccountException.
     */
    @Test(expected = AccountException.class)
    public void withdrawingFromAccountWhenCustomerIdsMismatchThrowsAccountException() {
        CheckingAccount mockAccount = Mockito.mock(CheckingAccount.class);
        when(mockAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockAccount.getCustomerId()).thenReturn(this.dummyCustomerId);

        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockAccount);

        int accountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        this.accountManager.withdraw(this.dummyOtherCustomerId, accountId, this.dummyAmount);
    }

    /**
     * Depositing into an account deposits into the account
     */
    @Test
    public void depositingIntoAccountDepositsIntoAccount() {
        CheckingAccount mockAccount = Mockito.mock(CheckingAccount.class);
        when(mockAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockAccount.getCustomerId()).thenReturn(this.dummyCustomerId);

        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockAccount);

        int accountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        this.accountManager.deposit(this.dummyCustomerId, accountId, this.dummyAmount);

        Mockito.verify(mockAccount).deposit(this.dummyAmount);
    }

    /**
     * Depositing into account when no account exists throws an AccountException.
     */
    @Test(expected = AccountException.class)
    public void depositingIntoAccountWhenNoAccountExistsThrowsAccountException() {
        this.accountManager.deposit(this.dummyCustomerId, this.dummyAccountId, this.dummyAmount);
    }

    /**
     * Depositing into account when the given customer ID doesn't match the account's customer ID
     * throws an AccountException.
     */
    @Test(expected = AccountException.class)
    public void depositingIntoAccountWhenCustomerIdsMismatchThrowsAccountException() {
        CheckingAccount mockAccount = Mockito.mock(CheckingAccount.class);
        when(mockAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockAccount.getCustomerId()).thenReturn(this.dummyCustomerId);

        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockAccount);

        int accountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        this.accountManager.deposit(this.dummyOtherCustomerId, accountId, this.dummyAmount);
    }

    /**
     * Tests that transferring between accounts transfers between accounts.
     */
    @Test
    public void transferringBetweenAccountsTransfersBetweenAccounts() {
        CheckingAccount mockSourceAccount = Mockito.mock(CheckingAccount.class);
        when(mockSourceAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockSourceAccount.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockSourceAccount);

        int sourceAccountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        CheckingAccount mockDestinationAccount = Mockito.mock(CheckingAccount.class);
        when(mockDestinationAccount.getAccountId()).thenReturn(this.dummyOtherAccountId);
        when(mockDestinationAccount.getCustomerId()).thenReturn(this.dummyOtherCustomerId);
        when(this.mockAccountIdManager.generateAccountId()).thenReturn(this.dummyOtherAccountId);
        when(this.mockAccountFactory.createCheckingAccount(this.dummyOtherAccountId, this.dummyOtherCustomerId))
                .thenReturn(mockDestinationAccount);

        int destinationAccountId = this.accountManager.openAccount(this.dummyOtherCustomerId, AccountType.CHECKING);

        this.accountManager.transfer(this.dummyCustomerId, sourceAccountId, destinationAccountId, this.dummyAmount);

        Mockito.verify(mockSourceAccount).withdraw(this.dummyAmount);
        Mockito.verify(mockDestinationAccount).deposit(this.dummyAmount);
    }

    /**
     * Tests that transferring between accounts when the source account does not exist
     * throws an AccountException.
     */
    @Test(expected = AccountException.class)
    public void transferringBetweenAccountsWhenSourceAccountDoesNotExistThrowsAccountException() {
        CheckingAccount mockDestinationAccount = Mockito.mock(CheckingAccount.class);
        when(mockDestinationAccount.getAccountId()).thenReturn(this.dummyOtherAccountId);
        when(mockDestinationAccount.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockAccountIdManager.generateAccountId()).thenReturn(this.dummyOtherAccountId);
        when(this.mockAccountFactory.createCheckingAccount(this.dummyOtherAccountId, this.dummyCustomerId))
                .thenReturn(mockDestinationAccount);

        int destinationAccountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        this.accountManager.transfer(this.dummyCustomerId, 0, destinationAccountId, this.dummyAmount);
    }

    /**
     * Tests that transferring between account when the source account's customer ID does not match
     * the given customer ID throws an AccountException.
     */
    @Test(expected = AccountException.class)
    public void transferringBetweenAccountsWhenSourceAccountCustomerIdDoesNotMatchGivenCustomerIdThrowsAccountException() {
        CheckingAccount mockSourceAccount = Mockito.mock(CheckingAccount.class);
        when(mockSourceAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockSourceAccount.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockSourceAccount);

        int sourceAccountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        CheckingAccount mockDestinationAccount = Mockito.mock(CheckingAccount.class);
        when(mockDestinationAccount.getAccountId()).thenReturn(this.dummyOtherAccountId);
        when(mockDestinationAccount.getCustomerId()).thenReturn(this.dummyOtherCustomerId);
        when(this.mockAccountIdManager.generateAccountId()).thenReturn(this.dummyOtherAccountId);
        when(this.mockAccountFactory.createCheckingAccount(this.dummyOtherAccountId, this.dummyOtherCustomerId))
                .thenReturn(mockDestinationAccount);

        int destinationAccountId = this.accountManager.openAccount(this.dummyOtherCustomerId, AccountType.CHECKING);

        this.accountManager.transfer(this.dummyOtherCustomerId, sourceAccountId, destinationAccountId, this.dummyAmount);
    }

    /**
     * Tests that transferring between accounts when the destination account does not exist
     * throws an AccountException.
     */
    @Test(expected = AccountException.class)
    public void transferringBetweenAccountsWhenDestinationAccountDoesNotExistThrowsAccountException() {
        CheckingAccount mockSourceAccount = Mockito.mock(CheckingAccount.class);
        when(mockSourceAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockSourceAccount.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockSourceAccount);

        int sourceAccountId = this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        this.accountManager.transfer(this.dummyCustomerId, sourceAccountId, 0, this.dummyAmount);
    }

    /**
     * Getting the total interest earned gets the interest earned on accounts.
     */
    @Test
    public void gettingTotalInterestEarnedGetsInterestEarnedOnAccounts() {
        CheckingAccount mockCheckingAccount = Mockito.mock(CheckingAccount.class);
        when(mockCheckingAccount.getAccountId()).thenReturn(this.dummyAccountId);
        when(mockCheckingAccount.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockAccountFactory.createCheckingAccount(this.dummyAccountId, this.dummyCustomerId))
                .thenReturn(mockCheckingAccount);

        this.accountManager.openAccount(this.dummyCustomerId, AccountType.CHECKING);

        SavingsAccount mockSavingsAccount = Mockito.mock(SavingsAccount.class);
        when(mockSavingsAccount.getAccountId()).thenReturn(this.dummyOtherAccountId);
        when(mockSavingsAccount.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockAccountIdManager.generateAccountId()).thenReturn(this.dummyOtherAccountId);
        when(this.mockAccountFactory.createSavingsAccount(this.dummyOtherAccountId, this.dummyCustomerId))
                .thenReturn(mockSavingsAccount);

        this.accountManager.openAccount(this.dummyCustomerId, AccountType.SAVINGS);

        this.accountManager.calculateTotalInterestEarned(this.dummyCustomerId);

        Mockito.verify(mockCheckingAccount).calculateInterestEarned();
        Mockito.verify(mockSavingsAccount).calculateInterestEarned();
    }
}
