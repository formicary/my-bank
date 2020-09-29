package com.abc;

import com.abc.entity.Account;
import com.abc.entity.AccountType;
import com.abc.entity.Customer;
import com.abc.exception.InvalidAccountException;
import com.abc.exception.InvalidAmountException;
import com.abc.exception.InvalidCustomerException;
import com.abc.service.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static Customer customer;
    private static Account currentAccount;
    private static Account savingsAccount;
    private static Account maxiSavingsAccount;


    @Before
    public void setup(){
        customer = new Customer("Customer A");
        currentAccount = new Account(AccountType.CURRENT);
        savingsAccount = new Account(AccountType.SAVINGS);
        maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);

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
        customer.addAccount(new Account(AccountType.CURRENT));
        assertEquals("Mutliple customer checking accounts are not added to account list", 2, customer.getAccounts().size());
    }

    @Test
    public void customerCanAddMultipleSavingsAccount(){
        customer.addAccount(savingsAccount);
        customer.addAccount(new Account(AccountType.SAVINGS));
        assertEquals("Multiple customer savings accounts are not added to account list", 2, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleMaxiSavingsAccount(){
        customer.addAccount(maxiSavingsAccount);
        customer.addAccount(new Account(AccountType.MAXI_SAVINGS));
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
        TransactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        assertEquals("A single transaction is not accumulated in the sum of transactions.", new BigDecimal("10.99"), TransactionManager.sumTransactions(currentAccount));
    }

    @Test
    public void multipleTransactionAddedToBalance(){
        customer.addAccount( currentAccount);
        TransactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        TransactionManager.deposit(currentAccount, new BigDecimal("2.00"));
        assertEquals("Multiple transactions are not accumulated in the sum of transactions.", new BigDecimal("12.99"), TransactionManager.sumTransactions(currentAccount));
    }

    @Test(expected = InvalidAmountException.class)
    public void zeroDepositAmountThrowsException(){
        customer.addAccount( currentAccount);
        TransactionManager.deposit(currentAccount, new BigDecimal("0"));
   }

    @Test(expected = InvalidAmountException.class)
    public void nullDepositAmountThrowsException(){
        customer.addAccount( currentAccount);
        TransactionManager.deposit(currentAccount, null);
    }

    @Test(expected = InvalidAmountException.class)
    public void negativeDepositAmountThrowsException(){
        customer.addAccount(currentAccount);
        TransactionManager.deposit(currentAccount, new BigDecimal("-1.0"));
    }

    @Test(expected = InvalidAccountException.class)
    public void nullDepositAccountThrowsException(){
        customer.addAccount( currentAccount);
        TransactionManager.deposit(null, new BigDecimal("1.0"));
    }


    @Test
    public void canMakeWithdrawalFromAccount(){
        customer.addAccount( currentAccount);
        TransactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        TransactionManager.withdraw(currentAccount, new BigDecimal("2.00"));
        assertEquals("Single withdrawal from account is not reflected in sum transactions.", new BigDecimal("8.99"), TransactionManager.sumTransactions(currentAccount));
    }

    @Test
    public void canMakeTwoWithdrawalFromAccount(){
        customer.addAccount( currentAccount);
        TransactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        TransactionManager.withdraw(currentAccount, new BigDecimal("2.00"));
        TransactionManager.withdraw(currentAccount, new BigDecimal("2.00"));
        assertEquals("Single withdrawal from account is not reflected in sum transactions.", new BigDecimal("6.99"), TransactionManager.sumTransactions(currentAccount));
    }

    @Test(expected = InvalidAccountException.class)
    public void withdrawFromEmptyAccountThrowsException(){
        customer.addAccount(currentAccount);
        TransactionManager.withdraw(currentAccount, new BigDecimal("1"));
    }

    @Test(expected = InvalidAmountException.class)
    public void zeroWithdrawalAmountThrowsException(){
        customer.addAccount( currentAccount);
        TransactionManager.withdraw(currentAccount, new BigDecimal("0"));
    }

    @Test(expected = InvalidAmountException.class)
    public void nullWithdrawalAmountThrowsException(){
        customer.addAccount( currentAccount);
        TransactionManager.withdraw(currentAccount, null);
    }

    @Test(expected = InvalidAmountException.class)
    public void negativeWithdrawalAmountThrowsException(){
        customer.addAccount( currentAccount);
        TransactionManager.withdraw(currentAccount, new BigDecimal("-1.0"));
    }

    @Test(expected = InvalidAccountException.class)
    public void nullWithdrawalAccountThrowsException(){
        customer.addAccount(currentAccount);
        TransactionManager.withdraw(null, new BigDecimal("1.0"));
    }




//    @Test //Test customer statement generation
//    public void testApp(){
//
//        Account checkingAccount = new Account(Account.CHECKING);
//        Account savingsAccount = new Account(Account.SAVINGS);
//
//        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
//
//        checkingAccount.deposit(100.0);
//        savingsAccount.deposit(4000.0);
//        savingsAccount.withdraw(200.0);
//
//        assertEquals("Statement for Henry\n" +
//                "\n" +
//                "Checking Account\n" +
//                "  deposit $100.00\n" +
//                "Total $100.00\n" +
//                "\n" +
//                "Savings Account\n" +
//                "  deposit $4,000.00\n" +
//                "  withdrawal $200.00\n" +
//                "Total $3,800.00\n" +
//                "\n" +
//                "Total In All Accounts $3,900.00", henry.getStatement());
//    }
//

}
