package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class AccountTest {
    
    private static final double DOUBLE_DELTA = 1e-15;
    
    /* deposit() and withdraw() tests */
    
    @Test
    public void negativeDeposit() {
        Account checkAcc = new Account(Account.CHECKING);
        try {
            checkAcc.deposit(-100.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("amount must be greater than zero", e.getMessage());
        }
    }
    
    @Test
    public void negativeWithdrawal() {
        Account checkAcc = new Account(Account.CHECKING);
        try {
            checkAcc.withdraw(-100.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("amount must be greater than zero", e.getMessage());
        }
    }
    
    /* sumTransactions() tests */
    
    @Test
    public void testDeposit() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(100.0);
        assertEquals(100.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
        
    @Test
    public void noTransactions() {
        Account checkAcc = new Account(Account.CHECKING);
        assertEquals(0, checkAcc.sumTransactions(), DOUBLE_DELTA);
    }
    
    /* getAccountType() tests */
    
    @Test
    public void testAccType() {
        Account checkAcc = new Account(Account.CHECKING);
        assertEquals(0, checkAcc.getAccountType(), DOUBLE_DELTA);
    }
    
    /* interestEarned() tests */
    
    @Test
    public void checkingInterest() {
        Account checkAcc = new Account(Account.CHECKING);
        checkAcc.deposit(1200.0);
        checkAcc.withdraw(200.0);
        assertEquals(1, checkAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void savingsInterest01() {
        Account saveAcc = new Account(Account.SAVINGS);
        saveAcc.deposit(900.0);
        assertEquals(0.9, saveAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void savingsInterest02() {
        Account saveAcc = new Account(Account.SAVINGS);
        saveAcc.deposit(2000.0);
        assertEquals(3, saveAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiInterestWithRecentWithdrawal() {
        Account maxiAcc = new Account(Account.MAXI_SAVINGS);
        maxiAcc.deposit(3000.0);
        maxiAcc.withdraw(1000);
        assertEquals(2, maxiAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiInterestWith10DayNoWithdrawal() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = formatter.parse("09-10-2019");
        
        Account maxiAcc = new Account(Account.MAXI_SAVINGS);
        maxiAcc.addTransaction(2000, date);
        maxiAcc.addTransaction(-500, date);
        
        assertEquals(75, maxiAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiInterestWithNullWithdrawals() {
        Account maxiAcc = new Account(Account.MAXI_SAVINGS);
        maxiAcc.deposit(3000.0);
        assertEquals(150, maxiAcc.interestEarned(), DOUBLE_DELTA);
    }
   
    @Test
    public void negativeInterest() {
        Account checkAcc = new Account(Account.CHECKING);
        checkAcc.withdraw(100.0);
        assertEquals(0, checkAcc.interestEarned(), DOUBLE_DELTA);
    }   
}
