package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static com.abc.AccountType.CHECKING;
import static com.abc.AccountType.MAXI_SAVINGS;
import static com.abc.AccountType.SAVINGS;
import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void shouldCreateNewCustomerWithoutAnyAccounts() {
        Customer cust = new Customer("Bill");
        assertEquals("Bill", cust.getName());
        assertEquals(0, cust.getNumberOfAccounts());
    }

    @Test //Test customer statement generation
    public void shouldGenerateStatement() {
        Account checkingAccount = new Account(CHECKING);
        Account savingsAccount = new Account(SAVINGS);

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
    public void shouldDisplayNegativeBalancesInStatements() {
        Account checkingAccount = new Account(CHECKING);
        Account savingsAccount = new Account(SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.withdraw(100.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  withdrawal $100.00\n" +
                "Total $-100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  withdrawal $200.00\n" +
                "Total $-200.00\n" +
                "\n" +
                "Total In All Accounts $-300.00", henry.getStatement());
    }

    @Test
    public void shouldDisplayAccountNamesInStatement() {
        Account checkingAccount = new Account(CHECKING);
        Account savingsAccount = new Account(SAVINGS);
        Account maxiSavings = new Account(MAXI_SAVINGS);

        Customer henry = new Customer("Henry")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount)
                .openAccount(maxiSavings);


        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $0.00", henry.getStatement());
    }

}
