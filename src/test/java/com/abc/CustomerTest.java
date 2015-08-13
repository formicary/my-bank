package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testStatement(){

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account checkingAccount2 = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);
        henry.openAccount(checkingAccount2);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        checkingAccount2.deposit(100.0);
        checkingAccount2.withdraw(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account:\n" +
                "  Deposit of $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Checking Account:\n" +
                "  Deposit of $100.00\n" +
                "  Withdrawal of $100.00\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings Account:\n" +
                "  Deposit of $4,000.00\n" +
                "  Withdrawal of $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testFourAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        oscar.openAccount(new Account(Account.AccountType.SUPER_SAVINGS));
//        oscar.openAccount(new Account(Account.AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
