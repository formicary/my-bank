package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import com.abc.Enums.AccountType;
import com.abc.Enums.TransactionType;
public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
        Double checkingDeposit = 100.0;
        Double savingsDeposit =4000.0;
        Double savingsWithdraw = 200.0;
        checkingAccount.deposit(checkingDeposit, TransactionType.DEPOSIT);
        savingsAccount.deposit(savingsDeposit, TransactionType.DEPOSIT);
        savingsAccount.withdraw(savingsWithdraw, TransactionType.WITHDRAW);
        System.out.println(savingsAccount.getBalance());

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
    public void testTransfer(){
        Customer oscar = new Customer("Oscar");
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        savingsAccount.deposit(100, TransactionType.DEPOSIT);
        oscar.transferBetween(savingsAccount, checkingAccount, 100);
        assertEquals(100,checkingAccount.getBalance(),DOUBLE_DELTA);
    }
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
