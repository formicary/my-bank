package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.abc.accounts.*;

public class CustomerTest {

    private Customer oscar;
    private Account savingsAccount;
    private Account checkingAccount;
    private Account maxiSavingsAccount;

    private static final double DOUBLE_DELTA = 1e-15;

    @Before
    public void setup(){
        oscar = new Customer("Oscar");
        savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        maxiSavingsAccount = AccountFactory.createAccount(AccountType.MAXI_SAVINGS);

    }

    @Test //Test customer statement generation
    public void testApp(){
        oscar.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        assertEquals("Statement for Oscar\n" +
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
                "Total In All Accounts $3,900.00", oscar.getStatement());
    }

    @Test
    public void testOneAccount(){
        oscar.openAccount(AccountFactory.createAccount(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(AccountFactory.createAccount(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void getNumberOfAccounts_NoAccounts(){
        assertEquals(0, oscar.getNumberOfAccounts());
    }

    //transferMoney Tests ///////////////////////////////////////////

    @Test
    public void transferMoney(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        savingsAccount.deposit(500);
        oscar.transferMoney(savingsAccount, checkingAccount, 100);
        assertEquals(savingsAccount.sumTransactions(), 400, DOUBLE_DELTA);
        assertEquals(checkingAccount.sumTransactions(), 100, DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class )
    public void transferMoney_amoutIsZeroOrLess(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        oscar.transferMoney(savingsAccount, checkingAccount, 0);
    }

    @Test(expected = NullPointerException.class)
    public void transferMoney_accountFrom_doesNotExist(){
        oscar.openAccount(checkingAccount);
        oscar.transferMoney(null, checkingAccount, 40);
    }

    @Test(expected = NullPointerException.class)
    public void transferMoney_accountTo_doesNotExist(){
        oscar.openAccount(savingsAccount);
        oscar.transferMoney(savingsAccount, null, 40);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferMoney_sameAccount(){
        oscar.openAccount(savingsAccount);
        oscar.transferMoney(savingsAccount, savingsAccount, 40);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferMoney_insufficientFunds(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        savingsAccount.deposit(500);
        oscar.transferMoney(savingsAccount, checkingAccount, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferMoney_notCustomersAccount_accountFrom(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        savingsAccount.deposit(500);

        Customer bill = new Customer("Bill");
        Account billsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        bill.openAccount(billsAccount);
        
        oscar.transferMoney(savingsAccount, billsAccount, 300);
    }


    @Test(expected = IllegalArgumentException.class)
    public void transferMoney_notCustomersAccount_accountTo(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        savingsAccount.deposit(500);

        Customer bill = new Customer("Bill");
        Account billsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        bill.openAccount(billsAccount);
        billsAccount.deposit(1000);
        
        oscar.transferMoney(billsAccount, savingsAccount, 300);
    }


    @Test(expected = IllegalArgumentException.class)
    public void transferMoney_notCustomersAccount_bothAccounts(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        savingsAccount.deposit(500);

        Customer bill = new Customer("Bill");
        Account billsAccount1 = AccountFactory.createAccount(AccountType.SAVINGS);
        Account billsAccount2 = AccountFactory.createAccount(AccountType.CHECKING);
        bill.openAccount(billsAccount1);
        
        billsAccount1.deposit(1000);
        
        oscar.transferMoney(billsAccount1, billsAccount2, 300);
    }

    @Test
    public void accountSummary(){
        oscar.openAccount(checkingAccount);
        checkingAccount.deposit(1000);
        oscar.openAccount(savingsAccount);
        savingsAccount.deposit(500);

        oscar.openAccount(maxiSavingsAccount);
        maxiSavingsAccount.deposit(1000);

        String expectedSummary = "Account summary for Oscar" 
                                +"\n - Checking Account. Interest Earned: " + Utilities.toDollars(checkingAccount.interestEarned())
                                +"\n - Savings Account. Interest Earned: " + Utilities.toDollars(savingsAccount.interestEarned())
                                +"\n - Maxi savings Account. Interest Earned: " + Utilities.toDollars(maxiSavingsAccount.interestEarned())
                                +"\n Total interest earned: " + Utilities.toDollars(oscar.totalInterestEarned());

        assertEquals(expectedSummary, oscar.accountSummary());
        
    }




}
