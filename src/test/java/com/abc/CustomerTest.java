package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.CheckingAccount;
import com.abc.accounts.MaxiSavingsAccount;
import com.abc.accounts.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransfer(){
        Customer oscar = new Customer("Oscar");
        Account checkingAccountOne = new CheckingAccount();
        Account checkingAccountTwo = new CheckingAccount();
        oscar.openAccount(checkingAccountOne);
        oscar.openAccount(checkingAccountTwo);

        oscar.transferFunds(100, checkingAccountOne, checkingAccountTwo);

        assertEquals(-100, checkingAccountOne.sumTransactions(), 0.01);
        assertEquals(100, checkingAccountTwo.sumTransactions(), 0.01);
    }
}
