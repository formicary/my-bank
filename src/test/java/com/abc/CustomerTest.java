package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.addCustomer(john);
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", john.customerSummary(john));
    }


    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Conversion currentConversion = new Conversion();


        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(currentConversion.doubleToBigDecimalConverter(100.0));
//        System.out.println(henry.getStatement(savingsAccount));
        savingsAccount.deposit(currentConversion.doubleToBigDecimalConverter(4000.0));
//        System.out.println(henry.getStatement(savingsAccount));
        savingsAccount.withdraw(currentConversion.doubleToBigDecimalConverter(200.0));
        DateProvider d = new DateProvider();
        System.out.println(henry.getStatement());
        assertEquals("Henry's Statement: \n\n" +
                "Checking Account\n" +
                d.now().toString() +
                " deposit: $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                d.now().toString() +
                " deposit: $4,000.00\n" +
                d.now().toString() +
                " withdrawal: $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
//
//    @Ignore
//    public void testThreeAcounts() {
//        Customer oscar = new Customer("Oscar")
//                .openAccount(new Account(Account.SAVINGS));
//        oscar.openAccount(new Account(Account.CHECKING));
//        assertEquals(3, oscar.getNumberOfAccounts());
//    }
}
