/*
 * Test file for printed outputs
 * Ignore this file for application
 */

package com.abc;

import java.text.NumberFormat;
import java.util.Locale;

public class Main {

	public Main() {
	}

	public static void main(String[] args) {
		//Customer Tests
		Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry", Locale.US).openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        System.out.println(henry.getStatement());
        
		Account checkingAcc = new Account(Account.CHECKING);
        Account savingsAcc = new Account(Account.SAVINGS);
        Account maxiSavingsAcc = new Account(Account.MAXI_SAVINGS);

        Customer jane = new Customer("Jane", Locale.UK).openAccount(checkingAcc).openAccount(savingsAcc).openAccount(maxiSavingsAcc);

        checkingAcc.deposit(100.0);
        savingsAcc.deposit(4000.0);
        savingsAcc.withdraw(200.0);
        maxiSavingsAcc.deposit(3000.0);
        
        System.out.println(jane.getStatement());
        
        System.out.println("Jane\'s total interest: " + NumberFormat.getCurrencyInstance(jane.getLocale()).format(jane.totalInterestEarned()));
        
        System.out.println();

        //Bank Tests
        Bank bank = new Bank();
        Customer john = new Customer("John", Locale.US);
        Account johnChecking = new Account(Account.CHECKING);
        john.openAccount(johnChecking);
        bank.addCustomer(john);
        
        Customer jill = new Customer("Jill", Locale.US);
        Account jillSavings = new Account(Account.SAVINGS);
        Account jillMaxiSavings = new Account(Account.MAXI_SAVINGS);
        jill.openAccount(jillSavings).openAccount(jillMaxiSavings);
        bank.addCustomer(jill);
        
        System.out.println(bank.customerSummary() + "\n");
        
        johnChecking.deposit(1000.0);
        jillSavings.deposit(2000.0);
        jillMaxiSavings.deposit(3000.0);
        
        //System.out.println(john.totalInterestEarned());
        //System.out.println(jill.totalInterestEarned());
        System.out.println(bank.totalInterestPaid());
        
        System.out.println();
        
        Bank bankTwo = new Bank();
    	Account bobChecking = new Account(Account.CHECKING);
    	Account bobSavings = new Account(Account.SAVINGS);
    	Account janeSavings = new Account(Account.SAVINGS);
    	Account janeMaxiSavings = new Account(Account.MAXI_SAVINGS);
    	
    	bankTwo.addCustomer(new Customer("Bob", Locale.UK).openAccount(bobChecking).openAccount(bobSavings));
    	bankTwo.addCustomer(new Customer("Jane", Locale.UK).openAccount(janeSavings).openAccount(janeMaxiSavings));
    	
    	bobChecking.deposit(2000);
    	bobSavings.deposit(1000);
    	janeSavings.deposit(2000);
    	janeMaxiSavings.deposit(1000);
    	
    	System.out.println(bankTwo.totalInterestPaid());
    	
    	System.out.println();
    	
    	System.out.println("Bank's First Customer: " + bank.getFirstCustomer());
    	System.out.println("Bank Two's First Customer: " + bankTwo.getFirstCustomer());
    	
    	System.out.println();
    	
    	//Customer Additional Feature Test
    	Bank bankThree = new Bank();
	
    	Account harrySavings = new Account(Account.SAVINGS);
    	Account helenSavings = new Account(Account.SAVINGS);
    	Account helenChecking = new Account(Account.CHECKING);
    	
    	Customer harry = new Customer("Harry", Locale.UK).openAccount(harrySavings);
    	Customer helen = new Customer("Helen", Locale.UK).openAccount(helenSavings).openAccount(helenChecking);
    	
    	bankThree.addCustomer(harry);
    	bankThree.addCustomer(helen);
    	
    	helenChecking.deposit(1000);
    	helenSavings.deposit(100);
    	harrySavings.deposit(1000);
    	
    	System.out.println(helen.getStatement());
    	
    	helen.transferBetweenAccounts(100, helenChecking, helenSavings);
    
    	System.out.println(helen.getStatement());
    	
    	//Maxi-Savings Interest Rate Additional Feature
    	helenSavings.deposit(200);
    	helenSavings.deposit(4000);
    	System.out.println(helen.getStatement());
    	
    	System.out.println(helenSavings.isLastWithdrawal10DaysAfter());
    	
    	//Customer Interest Test
    	Account billyChecking = new Account(Account.CHECKING);
    	Account billyMaxiSavings = new Account(Account.MAXI_SAVINGS);
    	Customer billy = new Customer("Billy", Locale.US)
    			.openAccount(billyChecking);
    	billy.openAccount(billyMaxiSavings);
    	billyChecking.deposit(1000.0);
    	billyMaxiSavings.deposit(5000);
    	billyChecking.withdraw(50.0);
    	billyMaxiSavings.withdraw(10.0);
    	
    	
    	System.out.println();
    	System.out.println(billyMaxiSavings.isLastWithdrawal10DaysAfter());
    	System.out.println(NumberFormat.getCurrencyInstance(billy.getLocale()).format(billy.totalInterestEarned()));
	}

}
