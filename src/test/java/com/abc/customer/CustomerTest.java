package com.abc.customer;

import com.abc.account.Account;
import com.abc.account.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_CustomerHasMultipleAccounts_Expect_CustomerStatementToBeCorrect(){
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
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
                "Total In All Accounts $3,900.00", henry.getStatement());
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
        Customer jane = new Customer("Jane").openAccount(savingsAccount);
        savingsAccount.deposit(1500.0);
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        jane.openAccount(maxiSavingsAccount);
        maxiSavingsAccount.deposit(2500.0);

        assertEquals(122.0, jane.totalInterestEarned(), DELTA);
    }

}
