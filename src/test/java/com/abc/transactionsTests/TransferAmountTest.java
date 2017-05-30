package com.abc.transactionsTests;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.accounts.CheckingAccount;
import com.abc.accounts.SavingsAccount;
import com.abc.exceptions.*;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TransferAmountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }

    @Test
    public void transferMoneyToAnotherAccountTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account1 = TestUtils.createMaxiSavingsAccount(john);
        TestUtils.depositMoney(account1, 1000);

        Account account2 = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account2, 400);

        double balance = TestUtils.transferMoney(bank, account1, 1, 2, 500);

        assertEquals("The new balance of the account2 is 900$", 500, balance, DOUBLE_DELTA);
    }

    @Test(expected = IdenticalAccountIDException.class)
    public void transferMoneyToSameAccountTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account1 = TestUtils.createCheckingAccount(john);
        TestUtils.depositMoney(account1, 1000);

        Account account2 = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account2, 400);

        TestUtils.transferMoney(bank, account1, 1, 1, 500);
    }

    @Test(expected = NonPositiveAmountException.class)
    public void transferNegativeAmountTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account1 = TestUtils.createCheckingAccount(john);
        TestUtils.depositMoney(account1, 1000);

        Account account2 = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account2, 400);

        TestUtils.transferMoney(bank, account1, 1, 2, -500);
    }

    @Test(expected = NonPositiveAmountException.class)
    public void transferZeroAmountTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account1 = TestUtils.createCheckingAccount(john);
        TestUtils.depositMoney(account1, 1000);

        Account account2 = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account2, 400);

        TestUtils.transferMoney(bank, account1, 1, 2, 0);
    }


    @Test(expected = AccountNotExistException.class)
    public void transferMoneyToInvalidAccountTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Bank bank = new Bank("Lloyds");
        Customer john = new Customer("John");
        bank.getSystemManagement().addCustomer(john);

        Account account1 = new SavingsAccount();
        john.getAccountManagement().openAccount(account1);
        account1.deposit(1000);


        Account account2 = new CheckingAccount();
        john.getAccountManagement().openAccount(account2);
        account2.deposit(300);

        account1.transfer(1, 5, 500, bank);
    }

    @Test(expected = CustomerNotExistException.class)
    public void transferMoneyToInvalidCustomerTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Bank bank = new Bank("Lloyds");
        Customer john = new Customer("John");
        bank.getSystemManagement().addCustomer(john);

        Account account1 = new SavingsAccount();
        john.getAccountManagement().openAccount(account1);
        account1.deposit(1000);


        Account account2 = new CheckingAccount();
        john.getAccountManagement().openAccount(account2);
        account2.deposit(300);

        account1.transfer(3, 1, 500, bank);
    }

}
