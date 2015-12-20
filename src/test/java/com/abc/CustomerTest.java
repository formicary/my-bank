package com.abc;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() {

        Account checkingAccount = new Account(0);
        Account savingsAccount = new Account(1);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n"
                + "\n"
                + "Checking Account\n"
                + "  deposit $100.00\n"
                + "Total $100.00\n"
                + "\n"
                + "Savings Account\n"
                + "  deposit $4,000.00\n"
                + "  withdrawal $200.00\n"
                + "Total $3,800.00\n"
                + "\n"
                + "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(1));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(1));
        oscar.openAccount(new Account(0));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(1));
        oscar.openAccount(new Account(0));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransferBetweenAccounts() {
        Customer farhan = new Customer("Farhan").openAccount(new Account(0)).openAccount(new Account(1));
        List<Account> accounts;
        accounts = farhan.getAccounts();
        accounts.get(0).deposit(900);
        farhan.transferBetweenAccounts(accounts.get(0), accounts.get(1), 300);
        System.out.println(accounts.get(0).getBalance() + "," + accounts.get(1).getBalance());
        System.out.println("----------------------------------------------");

        List<Transaction> transactions = accounts.get(0).transactionSummary();
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println("Transactions for Account 1: " + transactions.get(i).getAmount());
        }
        System.out.println("Total in the Account 1:" + accounts.get(0).getBalance());

        System.out.println("----------------------------------------------");

        List<Transaction> transactions2 = accounts.get(1).transactionSummary();
        for (int i = 0; i < transactions2.size(); i++) {
            System.out.println("Transactions for Account 2: " + transactions2.get(i).getAmount());
        }
        System.out.println("Total in the Account 2:" + accounts.get(1).getBalance());

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        assertEquals(300, accounts.get(1).getBalance(), accounts.get(1).getBalance());
    }

}
