package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class BankApplication {

	public static void main(String[] args) {
		/*Bank bank = new Bank();
		List<Customer> customers = new ArrayList<Customer>();
		
		for( int i = 0; i < 10; i++) {
			customers.add(new Customer("Customer" + " " + i));
		}
			
		customers.forEach(new Consumer<Customer>() {
			@Override
			public void accept(Customer c) {
				Random rand = new Random(); 
				 
				c.openAccount(new Account(rand.nextInt(3)));
				c.getAccounts().get(0).deposit((Math.floor(rand.nextDouble() * 1000000)) / 100);
				c.getAccounts().get(0).withdraw(100);
				c.getStatement();
				System.out.println(c.getName() + ": " + c.getAccounts().get(0).getAccountType() + ", " + c.getAccounts().get(0).getCapital());
			}
			
		}); */
		
        /*Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        checkingAccount.deposit(3000.0);*/
        
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(10000.0);
        System.out.println(bank.totalInterestPaid());
        
        System.out.println(20.0/366);


	}

}
