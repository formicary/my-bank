package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.time.format.DateTimeFormatter;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00 - " + DateProvider.formattedDate(DateProvider.now()) + "\n" +
                "  deposit $100.00 - " + DateProvider.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n" +
                "Total $200.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00 - " + DateProvider.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n" +
                "  withdrawal $200.00 - " + DateProvider.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $4,000.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS)).openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
