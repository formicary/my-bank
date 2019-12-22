package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.AbstractCollection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        Date past = Utils.getDate("20/12/2018");
        Date now = DateProvider.getInstance().now();

        checkingAccount.deposit(100.0, past);
        savingsAccount.deposit(4000.0, past);
        savingsAccount.withdraw(200.0, past);


        //Balanced increased due to interest
        //Total is the amount of the transactions
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total transactions amount: $100.00\n" +
                "Balance: $100.10\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total transactions amount: $3,800.00\n" +
                "Balance: $3,807.65\n" +
                "\n" +
                "Total In All Accounts $3,907.75", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void transferTest(){
        Customer oscar = new Customer("Oscar");
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account checkingAccount = new Account(AccountType.CHECKING);
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);

        Date now = DateProvider.getInstance().now();

        savingsAccount.deposit(1000,now);
        checkingAccount.deposit(500,now);
        try {
            oscar.transferBetweenAccounts(oscar.getAccountIndex(savingsAccount),oscar.getAccountIndex(checkingAccount), 500.0, now);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(500, savingsAccount.getBalance(now), Utils.DOUBLE_DELTA);
        assertEquals(1000, checkingAccount.getBalance(now), Utils.DOUBLE_DELTA);

    }

    @Test
    public void testTotalCompundInterest(){
        Customer oscar = new Customer("Oscar");
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account checkingAccount = new Account(AccountType.CHECKING);
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);

        savingsAccount.deposit(1000,Utils.getDate("22/12/2018"));
        checkingAccount.deposit(1000,Utils.getDate("22/10/2019"));

        assertEquals(1.1676, oscar.totalCompoundInterestEarned(Utils.getDate("22/12/2019")), Utils.DOUBLE_DELTA);
    }
}
