package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class BankApplication {

	public static void main(String[] args) {
        
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(10000.0);
        System.out.println(bank.totalInterestPaid());
        
        System.out.println(20.0/366);
        
        bank.customerSummary();


	}

}
