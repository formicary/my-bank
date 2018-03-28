package test.java.com.abc;

import main.java.com.abc.Account;
import main.java.com.abc.Customer;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testStatement(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

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

    @Test //Test opening Savings Account
    public void testOneAccount(){
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(savingsAccount);

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test //Test opening Savings and Checking Account
    public void testTwoAccount(){
        Account savingsAccount = new Account(Account.SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);

        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test //Test opening Savings, Checking, and Maxi Savings Account
    public void testThreeAccounts() {
        Account savingsAccount = new Account(Account.SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(maxiSavingsAccount);

        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test //Test transferring money between Accounts
    public void testAccountTransfer() {
        Account savingsAccount = new Account(Account.SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);

        savingsAccount.deposit(100.0);

        oscar.transfer(savingsAccount, checkingAccount, 100.0);

        assertEquals(0.0, savingsAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(100.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
