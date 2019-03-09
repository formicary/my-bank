package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

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
    public void testTransfer(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        checkingAccount.deposit(9000.0);

        Customer chungus = new Customer("Chungus").openAccount(checkingAccount).openAccount(savingsAccount);

        chungus.transferBetweenAcounts(checkingAccount,savingsAccount,42.0);

        assertEquals(42, savingsAccount.sumTransactions(),0);
        assertEquals(9000-42,checkingAccount.sumTransactions(),0);
    }

    @Test
    public void testDailyChecking(){
        Account checkingAccount = new Account(Account.CHECKING);
        Customer chungus = new Customer("Chungus").openAccount(checkingAccount);
        checkingAccount.deposit(100);

        //apply for first day
        checkingAccount.applyInterestRate();
        assertEquals(100+(100*0.01/365), checkingAccount.sumTransactions(),0);

        //apply interest for rest of the year daily
        int d = 0;
        while(d < 364)
        {
            checkingAccount.applyInterestRate();
            d++;
        }
        
        assertEquals(101.005, checkingAccount.sumTransactions(),0.0001);
    }

    @Test
    public void testDailySaving(){
        Account savingAccount = new Account(Account.SAVINGS);
        Customer chungus = new Customer("Chungus").openAccount(savingAccount);
        savingAccount.deposit(990);
        
        int d = 0;
        int rateChangeDay = 183;
        double balance = 990;

        while(d<365)
        {
            if (savingAccount.sumTransactions()<=1000){

                savingAccount.applyInterestRate();
                balance += balance*0.01/365;

            }
            else{
                
                savingAccount.applyInterestRate();
                balance += balance*0.02/365;

            }

            d++;
        }
        assertEquals(balance, savingAccount.sumTransactions(),0);        
    }


    //test to find rate date change for savings account
    @Ignore
    public void findRateChangeSavings(){

        double x = 999;
        int index = 0;

        for(int i=0;i<365;i++){

            if (x<=1000){
                x+=x*(0.01/365);
            }
            else{
                x+=x*(0.02/365);
                index = i;

            }
        }

        assertEquals(100,index,0);
        
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
