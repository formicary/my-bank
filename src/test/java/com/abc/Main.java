package com.abc;

import java.text.NumberFormat;
import java.util.Locale;

public class Main {

	public Main() {
	}

	public static void main(String[] args) {
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


	}

}
