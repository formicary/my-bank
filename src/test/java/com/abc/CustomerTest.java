package com.abc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
       
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  UNKNOWN  deposit $100.00   date:"+strDate+"\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  UNKNOWN  deposit $4,000.00   date:"+strDate+"\n" +
                "  UNKNOWN  withdrawal $200.00   date:"+strDate+"\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }
    
    @Test
    public void testInterestAndTransfer(){
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
        String strDate = dateFormat.print(DateTime.now().toDateTime());
        //Test for 1 Year
        DateTime tt2 = DateTime.now().toDateTime().plusDays(-370);
        String strDate2 = dateFormat.print(tt2);
        
        String strDate3 = dateFormat.print(DateTime.now().toDateTime().plusDays(-5));
        
        
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS,4000.00,tt2);
        Account max_savingsAccount = new Account(Account.MAXI_SAVINGS,4000.00,tt2);
        
        
        //customer check
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(max_savingsAccount);
        checkingAccount.deposit(100.0);
        savingsAccount.withdraw(200.0,"LOAN");
        //max_savingsAccount.deposit(300,DateTime.now().toDateTime().plusDays(-5));
        max_savingsAccount.withdraw(200, DateTime.now().toDateTime().plusDays(-5));
        
        henry.transferMoney(1, 0, 500);
 
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  UNKNOWN  deposit $100.00   date:"+strDate+"\n" +
                "  TRANSFER  deposit $500.00   date:"+strDate+"\n" +
                "Total $600.00\n" +
                "\n" +
                "Savings Account\n" +
                "  INITIAL  deposit $4,000.00   date:"+strDate2+"\n" +
                "  INTEREST  deposit $8.11   date:"+strDate+"\n" +        
                "  LOAN  withdrawal $200.00   date:"+strDate+"\n" +
                "  TRANSFER  withdrawal $500.00   date:"+strDate+"\n" +
                "Total $3,308.11\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  INITIAL  deposit $4,000.00   date:"+strDate2+"\n" +
                "  INTEREST  deposit $200.00   date:"+strDate3+"\n" +      
                //Deposit is 5% interest, followed by withdrawal
                "  UNKNOWN  withdrawal $200.00   date:"+strDate3+"\n" +
                "  INTEREST  deposit $0.05   date:"+strDate+"\n" +
                "Total $4,000.05\n" +
                "\n" +
                "Total In All Accounts $7,908.16", henry.getStatement());
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
    
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAmounts(){
        Account save = new Account(Account.SAVINGS);
        Customer oscar = new Customer("Oscar")
                .openAccount(save);
        oscar.openAccount(new Account(Account.CHECKING));
        save.deposit(-200);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testtransferAmounts(){
        Account save = new Account(Account.SAVINGS);
        Customer oscar = new Customer("Oscar")
                .openAccount(save);
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.transferMoney(0, 3, 400);
    }
    
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    @Ignore
    public void testInterestAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
