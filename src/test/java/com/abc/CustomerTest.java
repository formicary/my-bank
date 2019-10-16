package com.abc;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        Account checking = new CheckingAccount();
        Account saving = new SavingsAccount();

        john.openAccount(checking);
        john.openAccount(saving);


        assertEquals("A customer summary for John: \n" +
                "Checking\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings\n" +
                "Total $0.00\n", john.customerSummary());
    }
//



    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Conversion currentConversion = new Conversion();


        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(currentConversion.doubleToBigDecimalConverter(100.0));
//        System.out.println(henry.getStatement(savingsAccount));
        savingsAccount.deposit(currentConversion.doubleToBigDecimalConverter(4000.0));
//        System.out.println(henry.getStatement(savingsAccount));
        savingsAccount.withdraw(currentConversion.doubleToBigDecimalConverter(200.0));
        DateProvider d = new DateProvider();
        System.out.println(henry.getStatement());
        assertEquals("Henry's Statement: \n" +
                "\n" +
                "Checking\n" +
                d.now().toString() + " DEPOSIT: $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings\n" +
                d.now().toString() + " DEPOSIT: $4,000.00\n" +
                d.now().toString() + " WITHRAW: $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Account savings = new SavingsAccount();
        Customer oscar = new Customer("Oscar").openAccount(savings);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Account savings = new SavingsAccount();
        Account checking = new CheckingAccount();
        Customer oscar = new Customer("Oscar")
                .openAccount(savings);
        oscar.openAccount(checking);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void moneyTransferSameCustomerTest() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);
        Account checking = new CheckingAccount();
        Account savings = new SavingsAccount();
        john.openAccount(checking);
        john.openAccount(savings);

        checking.deposit(Conversion.doubleToBigDecimalConverter(500));
        john.transferMoney(Conversion.doubleToBigDecimalConverter(400), savings, checking.accountId);
        System.out.println(john.getStatement());
        Assert.assertEquals(Conversion.doubleToBigDecimalConverter(400), savings.getAccountBalance());

    }

    @Test
    public void moneyTransferDifferentCustomerTest() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer jane = new Customer("Jane");
        bank.addCustomer(john);
        bank.addCustomer(jane);
        Account checking = new CheckingAccount();
        Account savings = new SavingsAccount();
        john.openAccount(checking);
        jane.openAccount(savings);

        checking.deposit(Conversion.doubleToBigDecimalConverter(500));
        john.transferMoney(Conversion.doubleToBigDecimalConverter(400), savings, checking.accountId);
        System.out.println(john.getStatement());
        System.out.println(jane.getStatement());
        Assert.assertEquals(Conversion.doubleToBigDecimalConverter(400), savings.getAccountBalance());

    }

}
