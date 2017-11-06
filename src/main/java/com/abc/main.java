package com.abc;

import java.util.ArrayList;
import java.util.List;

public class main {

public static void main(String[] args){


        Bank b = new Bank();
        Customer c = new Customer("Akeem");
        //account a1 starts with 1000
        Account a1 = new Account(Account.CHECKING);
        a1.deposit(1000);
        //Account a2 starts with 550
        Account a2 = new Account(Account.SAVINGS);
        a2.deposit(550);


        b.addCustomer(c);
        c.openAccount(a2);
        c.openAccount(a1);

        //you take amount 100 from account a2 and put it into a1
        c.AccountTransaction(100, a2, a1);

        //new balances.
        a1.gettotaltext();
        a2.gettotaltext();
        }


        }