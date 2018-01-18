package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
     private static final double DOUBLE_DELTA = 1e-15;
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        String s="Statement for Henry\n\n"+
"Checking Account:\n"+
"  deposit: $100.00\n"+
"SECULAR TOTAL: $100.00\n"+
"INTEREST ACCRUED: $0.00\n"+
"TOTAL BALANCE: $100.00\n\n"+

"Savings Account:\n"+
"  deposit: $4,000.00\n"+
"  withdrawal: $200.00\n"+
"SECULAR TOTAL: $3,800.00\n"+
"INTEREST ACCRUED: $0.00\n"+
"TOTAL BALANCE: $3,800.00\n\n"+
"Total In All Accounts: $3,900.00";
        assertEquals(s, henry.getStatement());
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
    public void testGetByAccountID(){
        Account account= new Account(Account.AccountType.SAVINGS);
        Customer oscar = new Customer("Oscar")
                .openAccount(account);
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(account, oscar.getAccountByID(oscar.getAccountIDs().get(0)));
    }
    
    @Test //Test customer statement generation
    public void transferBetweenAccounts(){

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        assertEquals(false, henry.transferBetweenAccounts(101,checkingAccount,savingsAccount));
        assertEquals(true,henry.transferBetweenAccounts(100,checkingAccount,savingsAccount));
        henry.transferBetweenAccounts(1000,savingsAccount,checkingAccount);
        assertEquals(1000,checkingAccount.getBalance(),DOUBLE_DELTA);
    }
}
