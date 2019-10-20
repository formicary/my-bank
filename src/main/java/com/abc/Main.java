package com.abc;

import java.util.Date;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//DateProvider myDate = DateProvider.getInstance();
		/*
		Calendar cal1 = DateProvider.now();
		System.out.println(cal1.get(Calendar.DAY_OF_YEAR));
		Calendar cal2 = DateProvider.nDaysAway(10);
		System.out.println(cal2.get(Calendar.DAY_OF_YEAR));
		Calendar cal3 = DateProvider.nDaysAway(-5);
		System.out.println(cal3.get(Calendar.DAY_OF_YEAR));
		
		long daysBetween = ChronoUnit.DAYS.between(cal1.toInstant(), cal3.toInstant());
		System.out.println(daysBetween);
		*/
		
		/*
		DateProvider time = DateProvider.getInstance();
		time.setDate(19, 8, 1997);
		System.out.println(time.now().getTime());
		DateProvider time2 = DateProvider.getInstance();
		time2.advanceDate(50);
		System.out.println(time2.now().getTime());
		*/
		
		
		//Calendar date = Calendar.getInstance();
		//System.out.println(date.getTime());
		//date.add(Calendar.DAY_OF_YEAR, 350);
		//System.out.println(date.getTime());		
		
		/*
		Account myAccount = new Account(1);
		double myMoney = 900;
		myAccount.deposit(myMoney);
		System.out.println(myAccount.sumTransactions());
		*/
		
		//checking account
		/*
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(100.0);
        DateProvider.getInstance().advanceDate(5);
        checkingAccount.compounding();
        System.out.println(bank.totalInterestPaid());
        */
		
		//savings account
		/*
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);
        DateProvider.getInstance().advanceDate(23); // 0.12595475702408976
        checkingAccount.withdraw(200.0);
        DateProvider.getInstance().advanceDate(12);
        checkingAccount.deposit(3000.0);
        DateProvider.getInstance().advanceDate(17);
        checkingAccount.compounding();
        System.out.println(bank.totalInterestPaid());
        */
		
		//maxi savings account
		/*
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        DateProvider.getInstance().advanceDate(7);
        checkingAccount.withdraw(300.0);
        DateProvider.getInstance().advanceDate(12);
        
        checkingAccount.compounding();
        
        System.out.println(bank.totalInterestPaid());
        */

	}

}
