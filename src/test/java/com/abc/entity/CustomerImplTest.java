package com.abc.entity;

import com.abc.entity.impl.AccountImpl;
import com.abc.entity.impl.CustomerImpl;
import com.abc.exception.InvalidAccountException;
import com.abc.exception.InvalidAmountException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerImplTest {

    private static Customer customer;
    private static AccountImpl currentAccount;
    private static AccountImpl savingsAccount;
    private static AccountImpl maxiSavingsAccount;

    @Before
    public void setup(){
        customer = new CustomerImpl("Customer A");

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
        currentAccount = new AccountImpl(customer,AccountType.CURRENT, "123");

        assertEquals("Customer checking account is not added to account list", 1, customer.getAccounts().size());
    }

    @Test
    public void customerCanAddSavingsAccount(){
        savingsAccount = new AccountImpl(customer,AccountType.SAVINGS, "123");

        assertEquals("Customer savings account is not added to account list", 1, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMaxiSavingsAccount(){
        maxiSavingsAccount = new AccountImpl(customer,AccountType.MAXI_SAVINGS, "123");
        assertEquals("Customer maxi savings account is not added to account list", 1, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleCheckingAccount(){
        currentAccount = new AccountImpl(customer,AccountType.CURRENT, "123");
        currentAccount = new AccountImpl(customer,AccountType.CURRENT, "124");
        assertEquals("Multiple customer checking accounts are not added to account list", 2, customer.getAccounts().size());
    }

    @Test
    public void customerCanAddMultipleSavingsAccount(){
        savingsAccount = new AccountImpl(customer,AccountType.SAVINGS, "123");
        savingsAccount = new AccountImpl(customer,AccountType.SAVINGS, "124");
        assertEquals("Multiple customer savings accounts are not added to account list", 2, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleMaxiSavingsAccount(){
        Account account1 = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "123");
        Account account2 = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "124");
        assertEquals("Multiple customer maxi savings accounts are not added to account list", 2, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleAccountsOfDifferentTypes(){
        Account account1 = new AccountImpl(customer, AccountType.CURRENT, "123");
        Account account2 = new AccountImpl(customer, AccountType.SAVINGS, "124");
        Account account3 = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "125");

        assertEquals("Multiple accounts of different types are not added to account list", 3, customer.getAccounts().size());

    }



    @Test(expected = InvalidAmountException.class)
    public void cannotHaveZeroValueForDeposit(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        customer.deposit(new BigDecimal("0"), currentAccount);
    }

    @Test(expected = InvalidAmountException.class)
    public void cannotHaveNullValueForDeposit(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");

        customer.deposit(null,
                currentAccount);
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotHaveNullAccountForDeposit(){
        customer.deposit(new BigDecimal("1.0"), null);
    }


    @Test
    public void canMakeWithdrawalFromAccount(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        customer.deposit(new BigDecimal("10.99"), currentAccount);
        customer.withdraw(new BigDecimal("2.00"), currentAccount);
        assertEquals("Single withdrawal from account is not reflected in sum transactions.", new BigDecimal("8.99"), currentAccount.calculateBalance());
    }

    @Test
    public void canMakeTwoWithdrawalFromAccount(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        customer.deposit(new BigDecimal("10.99"), currentAccount);
        customer.withdraw(new BigDecimal("2.00"), currentAccount);
        customer.withdraw(new BigDecimal("2.00"),currentAccount);
        assertEquals("Two withdrawals from account is not reflected in sum transactions.",
                new BigDecimal("6.99"),
                currentAccount.calculateBalance());
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotWithdrawFromEmptyAccount(){
        customer.withdraw(new BigDecimal("1"), currentAccount);
    }

    @Test(expected = InvalidAmountException.class)
    public void cannotWithdrawAmountOfZero(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        customer.deposit(new BigDecimal("10"), currentAccount);
        customer.withdraw( new BigDecimal("0"), currentAccount);
    }

    @Test(expected = InvalidAmountException.class)
    public void cannotWithdrawNullAmount(){

        customer.withdraw(null, currentAccount);
    }

    @Test(expected = InvalidAmountException.class)
    public void cannotWithdrawNegativeAmount(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");

        customer.withdraw( new BigDecimal("-1.0"),currentAccount);
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotWithdrawAgainstNullAccount(){
        customer.withdraw(new BigDecimal("1.0"), null);
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotTransferFromNullAccount(){
        customer.transfer(new BigDecimal("10.00"), null, currentAccount );
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotTransferToNullAccount(){
        customer.transfer( new BigDecimal("10.00"),currentAccount, null);
    }

    @Test(expected = InvalidAccountException.class)
    public void cannotTransferBetweenSameAccount(){
        customer.transfer( new BigDecimal("10.00"), currentAccount, currentAccount);
    }

    @Test(expected = InvalidAmountException.class)
    public void cannotTransferNullAmount(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        savingsAccount = new AccountImpl(customer, AccountType.SAVINGS, "124");
        customer.transfer(null, savingsAccount, currentAccount);
    }

    @Test(expected = InvalidAmountException.class)
    public void cannotTransferNegativeAmount(){
        customer.transfer(new BigDecimal("-1.0"),savingsAccount, currentAccount);
    }

    @Test
    public void transferWithdrawsFunds(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        savingsAccount = new AccountImpl(customer, AccountType.SAVINGS, "124");

        customer.deposit(new BigDecimal("10.00"),currentAccount);
        customer.transfer(new BigDecimal("3.00"),currentAccount, savingsAccount);
        assertEquals("Transfer does not withdraw funds as expected",
                new BigDecimal("7.00"),
                currentAccount.calculateBalance());
    }

    @Test
    public void transferDepositsFunds(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        savingsAccount = new AccountImpl(customer, AccountType.SAVINGS, "124");

        customer.deposit(new BigDecimal("10.00"),currentAccount);
        customer.transfer(new BigDecimal("3.00"),currentAccount, savingsAccount);
        assertEquals("Transfer does not withdraw funds as expected",
                new BigDecimal("3.00"),
                savingsAccount.calculateBalance());
    }

    @Test(expected = InvalidAmountException.class)
    public void cannotTransferFundsMoreThanBalance(){
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        savingsAccount = new AccountImpl(customer, AccountType.SAVINGS, "124");

        customer.deposit(new BigDecimal("10.00"),currentAccount );
        customer.transfer(new BigDecimal("30.00"),currentAccount, savingsAccount);

    }
}
