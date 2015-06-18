package com.abc;

public class Test {
	public static void main(String args[]){
		System.out.println(DateProvider.getInstance().now());
		Account savingsAccount = new Account(Account.SAVINGS);
		Customer oscar = new Customer("Oscar").openAccount(savingsAccount);
		System.out.println(oscar.getStatement());
		
		savingsAccount.deposit(2200.0, false);
		System.out.println(oscar.getStatement());
		
		
		
	}
}
