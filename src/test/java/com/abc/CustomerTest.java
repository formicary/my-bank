package com.abc;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountTypes.CHECKING);
        Account savingsAccount = new Account(AccountTypes.SAVINGS);

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

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
    public void testAccountTypes() {
        Customer oscar = new Customer("Oscar");
        List<Account> accounts = oscar.getAccounts();
        oscar.openAccount(new Account(AccountTypes.SAVINGS));
        assertEquals(AccountTypes.SAVINGS, accounts.get(0).getAccountType());

        oscar.openAccount(new Account(AccountTypes.CHECKING));
        assertEquals(AccountTypes.CHECKING, accounts.get(1).getAccountType());

        oscar.openAccount(new Account(AccountTypes.MAXI_SAVINGS));
        assertEquals(AccountTypes.MAXI_SAVINGS, accounts.get(2).getAccountType());
    }
}
