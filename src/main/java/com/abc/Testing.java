package com.abc;

import java.util.*;


public class Testing {
	private List<Account> accounts; 
    public static void main(String[] args) {
       Customer oscar = new Customer("Oscar");
       CheckingAccount account1 = new CheckingAccount();
       SavingsAccount account2 = new SavingsAccount();
       oscar.openAccount(account1);
       oscar.openAccount(account2);
       Account account3 = new CheckingAccount();
       Account account4 = new SavingsAccount();
       oscar.openAccount(account3);
       oscar.openAccount(account4);
       
       System.out.println(oscar.getNumberOfAccounts());
       account1.deposit(1000);
       account3.deposit(3000);
       account4.deposit(4000);
       double total = oscar.totalInterestEarned();
       
       System.out.println(total);
       
       System.out.println(oscar.getStatement());
       
    }
}
