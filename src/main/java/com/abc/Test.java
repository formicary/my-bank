package com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	
	
	
	public static void main(String[] args) throws ParseException {
		Bank bank = new Bank();
		Account maxiSavings = new Account(Account.MAXI_SAVINGS);
		Account savings = new Account(Account.SAVINGS);
	//	Account checking = new Account(Account.CHECKING);
		Customer customer1 = new Customer("shobhit");
		customer1.openAccount(maxiSavings);
	//	customer1.openAccount(savings);
	//	customer1.openAccount(checking);
		bank.addCustomer(customer1);
		maxiSavings.deposit(1000.0);
		savings.deposit(500.0);
	//	checking.deposit(300.0);
		customer1.trasferBetweenAccounts(maxiSavings, savings, 200.0);
		System.out.println(customer1.getStatement());
		System.out.println("#########################");
		System.out.println(bank.customerSummary());
		System.out.println(bank.totalInterestPaid());
		
	}
	
	/*public static void main(String[] args) throws InterruptedException {
		Date date1 = DateProvider.getInstance().now();
		Thread.sleep(10000);
		Date date2 = DateProvider.getInstance().now().;
		Calendar cal = new Calendar()
		System.out.println((date1.after(date2))?date1:date2);
		
		SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
		 String dateBeforeString = "31 01 2014";
		 String dateAfterString = "02 02 2015";

		 try {
		       Date dateBefore = myFormat.parse(dateBeforeString);
		       Date dateAfter = myFormat.parse(dateAfterString);
		       long difference = dateAfter.getTime() - dateBefore.getTime();
		       long daysBetween = (difference / (1000*60*60*24));
	                You can also convert the milliseconds to days using this method
	                * float daysBetween = 
	                *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
	                
		       System.out.println("Number of Days between dates: "+daysBetween);
		 } catch (Exception e) {
		       e.printStackTrace();
		 }
	   }*/
		

}
