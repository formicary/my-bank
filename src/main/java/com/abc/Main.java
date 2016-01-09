package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        System.out.println(bank.customerSummary());
    }
    
}
