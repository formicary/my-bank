package com.abc.entity;

import com.abc.entity.impl.AccountImpl;
import com.abc.entity.impl.CustomerImpl;
import com.abc.exception.InvalidAccountException;
import com.abc.exception.InvalidTransactionException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    private static Customer customer;
    private static Account currentAccount;

    @Before
    public void setup(){
        customer = new CustomerImpl("customer");
        currentAccount = new AccountImpl(customer,AccountType.CURRENT, "124");
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotCreateNullAccountType(){
        Account account = new AccountImpl(new CustomerImpl("customer"), null, "123");
    }

    @Test(expected = InvalidTransactionException.class)
    public void addingTransactionCannotBeNull(){
        Account account = new AccountImpl(new CustomerImpl("customer"), AccountType.CURRENT, "123");
        account.addTransaction(null);
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotHaveMultipleAccountsWithSameNumber(){
        Account account = new AccountImpl(customer, AccountType.CURRENT, "123");
        Account account2 = new AccountImpl(customer, AccountType.CURRENT, "123");
    }

    @Test
    public void newAccountHasZeroBalance(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        assertEquals("A new account does not have 0 sum of transactions.",
                new BigDecimal("0.00"),
                currentAccount.calculateBalance());
    }

    @Test
    public void singleTransactionAddedToBalanceForCurrentAccount(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        customer.deposit(new BigDecimal("10.99"), currentAccount);
        assertEquals("A single transaction is not accumulated in the sum of transactions for current account.",
                new BigDecimal("10.99"),
                currentAccount.calculateBalance());
    }

    @Test
    public void singleTransactionAddedToBalanceForSavingsAccount(){
        Account savings = new AccountImpl(customer, AccountType.SAVINGS, "125");
        customer.deposit(new BigDecimal("10.99"), savings);
        assertEquals("A single transaction is not accumulated in the sum of transactions for savings account.",
                new BigDecimal("10.99"),
                savings.calculateBalance());
    }

    @Test
    public void singleTransactionAddedToBalanceForMaxiSavingsAccount(){
        Account maxiSavingsAccount = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "125");
        customer.deposit(new BigDecimal("10.99"), maxiSavingsAccount);
        assertEquals("A single transaction is not accumulated in the sum of transactions for maxi savings account.",
                new BigDecimal("10.99"),
                maxiSavingsAccount.calculateBalance());
    }

    @Test
    public void singleTransactionAddedToBalanceForMaxiSavingsAdditionalAccount(){
        Account maxiSavingsAdd = new AccountImpl(customer, AccountType.MAXI_SAVINGS_ADD, "125");
        customer.deposit(new BigDecimal("10.99"), maxiSavingsAdd);
        assertEquals("A single transaction is not accumulated in the sum of transactions for maxi savings additional account.",
                new BigDecimal("10.99"),
                maxiSavingsAdd.calculateBalance());
    }


    @Test
    public void multipleTransactionAddedToBalanceForCurrent(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        customer.deposit(new BigDecimal("10.99"), currentAccount );
        customer.deposit(new BigDecimal("2.00"), currentAccount);
        assertEquals("Multiple transactions are not accumulated in the sum of transactions.", new BigDecimal("12.99"),
                currentAccount.calculateBalance());
    }


    @Test
    public void multipleTransactionAddedToBalanceForSavingsAccount(){
        Account savings = new AccountImpl(customer, AccountType.SAVINGS, "125");
        customer.deposit(new BigDecimal("10.99"), savings);
        customer.deposit(new BigDecimal("2.00"), savings);
        assertEquals("Multiple transactions is not accumulated in the sum of transactions for savings account.",
                new BigDecimal("12.99"),
                savings.calculateBalance());
    }

    @Test
    public void multipleTransactionAddedToBalanceForMaxiSavingsAccount(){
        Account maxiSavingsAccount = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "125");
        customer.deposit(new BigDecimal("10.99"), maxiSavingsAccount);
        customer.deposit(new BigDecimal("2.00"), maxiSavingsAccount);
        assertEquals("Multiple transactions is not accumulated in the sum of transactions for maxi savings account.",
                new BigDecimal("12.99"),
                maxiSavingsAccount.calculateBalance());
    }

    @Test
    public void multipleTransactionAddedToBalanceForMaxiSavingsAdditionalAccount(){
        Account maxiSavingsAdd = new AccountImpl(customer, AccountType.MAXI_SAVINGS_ADD, "125");
        customer.deposit(new BigDecimal("10.99"), maxiSavingsAdd);
        customer.deposit(new BigDecimal("2.00"), maxiSavingsAdd);
        assertEquals("Multiple transactions is not accumulated in the sum of transactions for maxi savings additional account.",
                new BigDecimal("12.99"),
                maxiSavingsAdd.calculateBalance());
    }
}
