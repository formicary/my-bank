package com.abc;

import com.abc.entity.impl.AccountImpl;
import com.abc.entity.impl.AccountType;
import com.abc.entity.impl.CustomerImpl;
import com.abc.exception.InvalidAccountException;
import com.abc.exception.InvalidAmountException;
import com.abc.service.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerImplTest {

    private static CustomerImpl customer;
    private static AccountImpl currentAccount;
    private static AccountImpl savingsAccount;
    private static AccountImpl maxiSavingsAccount;
    private static TransactionManager transactionManager;

    @Before
    public void setup(){
        customer = new CustomerImpl("Customer A");
        currentAccount = new AccountImpl(AccountType.CURRENT);
        savingsAccount = new AccountImpl(AccountType.SAVINGS);
        maxiSavingsAccount = new AccountImpl(AccountType.MAXI_SAVINGS);
        transactionManager = new TransactionManager(customer);
    }

    @Test
    public void newCustomerHasZeroAccounts(){
        assertEquals("New customer does not have 0 accounts", 0, customer.getAccounts().size());
    }

    @Test(expected = InvalidAccountException.class)
    public void accountCannotBeNullForNewAccount(){
        customer.addAccount( null);
    }

    @Test
    public void customerCanAddCheckingAccount(){
        customer.addAccount( currentAccount);
        assertEquals("Customer checking account is not added to account list", 1, customer.getAccounts().size());
    }

    @Test
    public void customerCanAddSavingsAccount(){
        customer.addAccount(savingsAccount);
        assertEquals("Customer savings account is not added to account list", 1, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMaxiSavingsAccount(){
        customer.addAccount(maxiSavingsAccount);
        assertEquals("Customer maxi savings account is not added to account list", 1, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleCheckingAccount(){
        customer.addAccount(currentAccount);
        customer.addAccount(new AccountImpl(AccountType.CURRENT));
        assertEquals("Mutliple customer checking accounts are not added to account list", 2, customer.getAccounts().size());
    }

    @Test
    public void customerCanAddMultipleSavingsAccount(){
        customer.addAccount(savingsAccount);
        customer.addAccount(new AccountImpl(AccountType.SAVINGS));
        assertEquals("Multiple customer savings accounts are not added to account list", 2, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleMaxiSavingsAccount(){
        customer.addAccount(maxiSavingsAccount);
        customer.addAccount(new AccountImpl(AccountType.MAXI_SAVINGS));
        assertEquals("Multiple customer maxi savings accounts are not added to account list", 2, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleAccountsOfDifferentTypes(){
        customer.addAccount(currentAccount);
        customer.addAccount(savingsAccount);
        customer.addAccount(maxiSavingsAccount);
        assertEquals("Multiple accounts of different types are not added to account list", 3, customer.getAccounts().size());

    }

    @Test
    public void newAccountHasZeroBalance(){
        customer.addAccount(currentAccount);
        assertEquals("A new account does not have 0 sum of transactions.", new BigDecimal(0), TransactionManager.sumTransactions(currentAccount));
    }

    @Test
    public void singleTransactionAddedToBalance(){
        customer.addAccount( currentAccount);
        transactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        assertEquals("A single transaction is not accumulated in the sum of transactions.", new BigDecimal("10.99"), TransactionManager.sumTransactions(currentAccount));
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotDepositIfAccountNotAddedToCustomer(){
        transactionManager.deposit(currentAccount, new BigDecimal("1.99"));
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotWithdrawIfAccountNotAddedToCustomer(){
        transactionManager.withdraw(currentAccount, new BigDecimal("1.99"));
    }

    @Test
    public void multipleTransactionAddedToBalance(){
        customer.addAccount( currentAccount);
        transactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        transactionManager.deposit(currentAccount, new BigDecimal("2.00"));
        assertEquals("Multiple transactions are not accumulated in the sum of transactions.", new BigDecimal("12.99"), TransactionManager.sumTransactions(currentAccount));
    }

    @Test(expected = InvalidAmountException.class)
    public void zeroDepositAmountThrowsException(){
        customer.addAccount( currentAccount);
        transactionManager.deposit(currentAccount, new BigDecimal("0"));
   }

    @Test(expected = InvalidAmountException.class)
    public void nullDepositAmountThrowsException(){
        customer.addAccount( currentAccount);
        transactionManager.deposit(currentAccount, null);
    }

    @Test(expected = InvalidAmountException.class)
    public void negativeDepositAmountThrowsException(){
        customer.addAccount(currentAccount);
        transactionManager.deposit(currentAccount, new BigDecimal("-1.0"));
    }

    @Test(expected = InvalidAccountException.class)
    public void nullDepositAccountThrowsException(){
        customer.addAccount( currentAccount);
        transactionManager.deposit(null, new BigDecimal("1.0"));
    }


    @Test
    public void canMakeWithdrawalFromAccount(){
        customer.addAccount( currentAccount);
        transactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        transactionManager.withdraw(currentAccount, new BigDecimal("2.00"));
        assertEquals("Single withdrawal from account is not reflected in sum transactions.", new BigDecimal("8.99"), TransactionManager.sumTransactions(currentAccount));
    }

    @Test
    public void canMakeTwoWithdrawalFromAccount(){
        customer.addAccount( currentAccount);
        transactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        transactionManager.withdraw(currentAccount, new BigDecimal("2.00"));
        transactionManager.withdraw(currentAccount, new BigDecimal("2.00"));
        assertEquals("Single withdrawal from account is not reflected in sum transactions.", new BigDecimal("6.99"), TransactionManager.sumTransactions(currentAccount));
    }

    @Test(expected = InvalidAccountException.class)
    public void withdrawFromEmptyAccountThrowsException(){
        customer.addAccount(currentAccount);
        transactionManager.withdraw(currentAccount, new BigDecimal("1"));
    }

    @Test(expected = InvalidAmountException.class)
    public void zeroWithdrawalAmountThrowsException(){
        customer.addAccount( currentAccount);
        transactionManager.withdraw(currentAccount, new BigDecimal("0"));
    }

    @Test(expected = InvalidAmountException.class)
    public void nullWithdrawalAmountThrowsException(){
        customer.addAccount( currentAccount);
        transactionManager.withdraw(currentAccount, null);
    }

    @Test(expected = InvalidAmountException.class)
    public void negativeWithdrawalAmountThrowsException(){
        customer.addAccount( currentAccount);
        transactionManager.withdraw(currentAccount, new BigDecimal("-1.0"));
    }

    @Test(expected = InvalidAccountException.class)
    public void nullWithdrawalAccountThrowsException(){
        customer.addAccount(currentAccount);
        transactionManager.withdraw(null, new BigDecimal("1.0"));
    }

    @Test
    public void nullCustomerTransferThrowsException(){
        transactionManager.transfer(null, currentAccount, new BigDecimal("10.00"));
    }
}
