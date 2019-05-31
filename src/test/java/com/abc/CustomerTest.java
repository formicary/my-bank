package com.abc;

import org.junit.Ignore;
import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testStatement(){

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0, new Date());
        savingsAccount.deposit(4000.0, new Date());
        savingsAccount.withdraw(200.0, new Date());

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  " + Transaction.TransactionType.DEPOSIT + " $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  " + Transaction.TransactionType.DEPOSIT + " $4,000.00\n" +
                "  " + Transaction.TransactionType.WITHDRAWAL + " $200.00\n" +
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
    public void testTransferBeetweenAcounts() {
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0, new Date());
        savingsAccount.deposit(4000.0, new Date());
        savingsAccount.withdraw(200.0, new Date());
        henry.transferBeetweenAccounts(savingsAccount, checkingAccount, 500);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  " + Transaction.TransactionType.DEPOSIT + " $100.00\n" +
                "  " + Transaction.TransactionType.DEPOSIT + " $500.00\n" +
                "Total $600.00\n" +
                "\n" +
                "Savings Account\n" +
                "  " + Transaction.TransactionType.DEPOSIT + " $4,000.00\n" +
                "  " + Transaction.TransactionType.WITHDRAWAL + " $200.00\n" +
                "  " + Transaction.TransactionType.WITHDRAWAL + " $500.00\n" +
                "Total $3,300.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

}
