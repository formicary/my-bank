package com.abc;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;
import com.abc.util.IDateProvider;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.*;

public class CustomerShould {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void GenerateStatement_GivenTheyHaveOpenAccounts() {

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer customer = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

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
                "Total In All Accounts $3,900.00", customer.getStatement());
    }

    @Test
    public void BeAbleToOpenOneAccount() {
        Customer customer = new Customer("Oscar");

        customer.openAccount(new SavingsAccount());

        assertEquals(1, customer.getNumberOfAccounts());
    }

    @Test
    public void BeAbleToOpenTwoAccounts() {
        Customer customer = new Customer("Oscar");

        customer.openAccount(new SavingsAccount());
        customer.openAccount(new CheckingAccount());

        assertEquals(2, customer.getNumberOfAccounts());
    }

    @Test
    public void BeAbleToOpenThreeAccounts() {
        Customer customer = new Customer("Oscar");

        IDateProvider mockDateProvider = Mockito.mock(IDateProvider.class);


        customer.openAccount(new SavingsAccount());
        customer.openAccount(new CheckingAccount());
        customer.openAccount(new MaxiSavingsAccount(mockDateProvider));

        assertEquals(3, customer.getNumberOfAccounts());
    }

    @Test
    public void TransferMoneyBetweenAccounts() {
        Customer customer = new Customer("Test");

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);

        checkingAccount.deposit(100.0d);

        customer.transferMoney(checkingAccount, savingsAccount, 50.0d);

        assertEquals(checkingAccount.sumTransactions(), 50.0d, DOUBLE_DELTA);
        assertEquals(savingsAccount.sumTransactions(), 50.0d, DOUBLE_DELTA);
    }

    @Test
    public void NotChangeBalance_GivenWithdrawalPartOfTransferThrewException(){
        Customer customer = new Customer("Test");

        Account accountFrom = Mockito.mock(Account.class,
                Mockito.withSettings()
                        .useConstructor()
                        .defaultAnswer(Mockito.CALLS_REAL_METHODS));

        accountFrom.deposit(100);

        Account accountTo = Mockito.mock(Account.class,
                Mockito.withSettings()
                        .useConstructor()
                        .defaultAnswer(Mockito.CALLS_REAL_METHODS));

        doThrow(IllegalArgumentException.class).when(accountFrom).withdraw(anyDouble());

        customer.openAccount(accountFrom);
        customer.openAccount(accountTo);

        assertEquals(100, accountFrom.sumTransactions(), DOUBLE_DELTA);
        assertEquals(0, accountTo.sumTransactions(), DOUBLE_DELTA);

        try {
            customer.transferMoney(accountFrom, accountTo, 50);
        }catch (IllegalArgumentException e){

        }

        assertEquals(100, accountFrom.sumTransactions(), DOUBLE_DELTA);
        assertEquals(0, accountTo.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void NotChangeBalance_GivenDepositPartOfTransferThrewException(){
        Customer customer = new Customer("Test");

        Account accountFrom = Mockito.mock(Account.class,
                Mockito.withSettings()
                        .useConstructor()
                        .defaultAnswer(Mockito.CALLS_REAL_METHODS));

        accountFrom.deposit(100);

        Account accountTo = Mockito.mock(Account.class,
                Mockito.withSettings()
                        .useConstructor()
                        .defaultAnswer(Mockito.CALLS_REAL_METHODS));

        doThrow(IllegalArgumentException.class).when(accountTo).deposit(anyDouble());

        customer.openAccount(accountFrom);
        customer.openAccount(accountTo);

        try {
            customer.transferMoney(accountFrom, accountTo, 50);
        }catch (IllegalArgumentException e){

        }

        assertEquals(100, accountFrom.sumTransactions(), DOUBLE_DELTA);
        assertEquals(0, accountTo.sumTransactions(), DOUBLE_DELTA);
    }
}
