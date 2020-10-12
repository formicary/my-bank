package com.abc;

import org.junit.Test;

import static com.abc.BankTest.DOUBLE_DELTA;
import static org.junit.Assert.assertEquals;

public class CustomerTest {
    Account checkingAccount = new Account(Account.AccountType.CHECKING);
    Account savingsAccount = new Account(Account.AccountType.SAVINGS);
    Customer customer = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeWithdrw()
    {
        savingsAccount.withdraw(-200.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDeposit()
    {
        checkingAccount.deposit(-4000);
    }

    @Test //Test customer statement generation
    public void testCustomerOperations(){
        checkingAccount.deposit(4000);
        savingsAccount.deposit(4200);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $4,000.00\n" +
                "Total $4,000.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,200.00\n" +
                "  withdraw $200.00\n" +
                "Total $4,000.00\n" +
                "\n" +
                "Total In All Accounts $8,000.00", customer.getStatement());

        customer.transfer(checkingAccount,savingsAccount,1000);
        assertEquals(3000,checkingAccount.sumTransactions(),DOUBLE_DELTA);
        assertEquals(5000,savingsAccount.sumTransactions(),DOUBLE_DELTA);
    }
}
