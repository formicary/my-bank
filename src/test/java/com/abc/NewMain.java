package com.abc;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author LOTWF
 * 
 * THIS IS A SANDBOX TEST ENVIRONMENT
 * 
 * 
 * 
 * 
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // timediffcheck
        
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
        String strDate = dateFormat.print(DateTime.now().toDateTime());
        //Test for 1 Year
        DateTime tt2 = DateTime.now().toDateTime().plusDays(-370);
        String strDate2 = dateFormat.print(tt2);
        System.out.println(strDate2);
        String strDate3 = dateFormat.print(DateTime.now().toDateTime().plusDays(-5));
        System.out.println(strDate3);
        
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
        System.out.print(henry.getStatement());
        /*//TESTdailyinterest
        System.out.println(1+0.05);
        System.out.println((double) 1/365);
        System.out.println(Math.pow(1.05,(double) 1/365));
        System.out.println(helper.dailyInterest(1, 0.05));*/
    }
    
}
