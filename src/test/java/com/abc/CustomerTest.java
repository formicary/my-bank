package com.abc;

import java.math.BigDecimal;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(new BigDecimal(100));
        savingsAccount.deposit(new BigDecimal(4000));
        savingsAccount.withdraw(new BigDecimal(200));

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
