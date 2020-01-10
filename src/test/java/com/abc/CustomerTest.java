package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        // adjusted test assertion
        assertEquals(	"Statement for Henry\n" +
				        "\n" +
				        "Checking Account\n" +
				        "10/01/2020  Deposit    :    $100.00  |   $100.00\n" +
				        "Current Balance        :                 $100.00\n" +
				        "\n" +
				        "Savings Account\n" +
				        "10/01/2020  Deposit    :  $4,000.00  | $4,000.00\n" +
				        "10/01/2020  Withdrawal :    $200.00  | $3,800.00\n" +
				        "Current Balance        :               $3,800.00\n" +
				        "\n" +
				        "Total In All Accounts  :               $3,900.00", henry.getStatement());
        
        /* ORIGINAL TEST ASSERTION
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
         */
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testNonExistentTransaction() {    	
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer dylan = new Customer("Dylan").openAccount(checkingAccount).openAccount(savingsAccount);
        
        checkingAccount.deposit(500.0);
        savingsAccount.deposit(3000.0);
        savingsAccount.transfer(checkingAccount, 1000.0);

        int lastCheckingWithdrawalIndex; 
        
        System.out.println(dylan.getStatement());
        
        lastCheckingWithdrawalIndex = checkingAccount.getLatestTransactionIndex("withdrawal");	// latest withdrawal from checking
        
        assertEquals(lastCheckingWithdrawalIndex, -1);	// -1 is a value to signify non-existence (can't index at -1)           	
    }
    
    @Test
    public void testLatestExistingTransactionDate() {
    	final long DAYS_FROM_01_01_1970_TO_01_01_2020 = 18263;
    	
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Transaction t;        
        Date _10_01_2020;
        
        int lastSavingsWithdrawalIndex;  
        int daysSinceLastSavingsWithdrawal;
        
        checkingAccount.deposit(500.0);
        savingsAccount.deposit(3000.0);
        savingsAccount.transfer(checkingAccount, 1000.0);
        
        lastSavingsWithdrawalIndex = savingsAccount.getLatestTransactionIndex("withdrawal");			// latest withdrawal from savings
        t = savingsAccount.transactions.get(lastSavingsWithdrawalIndex);     							// get transaction from index
        _10_01_2020 = new Date(t.getDateProvider().DAYS * (DAYS_FROM_01_01_1970_TO_01_01_2020 + 9L));	// 10/01/2020
        daysSinceLastSavingsWithdrawal = savingsAccount.daysSinceTransaction(t, _10_01_2020);			// get number of days since deposit
                
        assertEquals(daysSinceLastSavingsWithdrawal, 0);    	
    }
    
    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testSavingsInterestOver1000() {
        Account savingsAccount = new Account(Account.SAVINGS);
        
        savingsAccount.deposit(2000.0);
        savingsAccount.deposit(1000.0);
        savingsAccount.deposit(3700.0);
        
        assertEquals(savingsAccount.interestEarned(), 12.4, DOUBLE_DELTA);	// [1 + (0.002 * 5,700]
        
    }
}
