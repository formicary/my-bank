package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){
        //Confusing order
        //First create the customer
        Customer henry = new Customer("Henry");

        //Next the accounts
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account check=new Account(Account.SAVINGS);
        //Attach them
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        //Manipulate the data
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        henry.transferBetweenAccounts(checkingAccount,savingsAccount,50.0);
        //Overdraw exception
        //henry.transferBetweenAccounts(checkingAccount,savingsAccount,1000);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "Total $50.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $50.00\n" +
                "Total $3,850.00\n" +
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
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    //A customer cannot transfer from the accounts of the other customer
    public void testHasAccess(){
        Customer henry = new Customer("Henry");
        Customer jerry = new Customer("Jerry");
        Account checkingAccount1 = new Account(Account.CHECKING);
        Account savingsAccount1 = new Account(Account.SAVINGS);
        Account checkingAccount2 = new Account(Account.CHECKING);
        Account savingsAccount2 = new Account(Account.SAVINGS);


        henry.openAccount(checkingAccount1);
        henry.openAccount(savingsAccount1);

        jerry.openAccount(checkingAccount2);
        jerry.openAccount(savingsAccount2);

        //Manipulate the data
        checkingAccount1.deposit(2000.0);
        savingsAccount1.deposit(2000.0);

        checkingAccount2.deposit(1000.0);
        savingsAccount2.deposit(1000.0);



        assertEquals("Done",henry.transferBetweenAccounts(checkingAccount1,savingsAccount1,10));
        assertEquals("No access",henry.transferBetweenAccounts(checkingAccount1,savingsAccount2,10));
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));

        assertEquals(3, oscar.getNumberOfAccounts());
    }



    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        Account checkingAccount = new Account(Account.CHECKING);
        bill.openAccount(checkingAccount);

        checkingAccount.deposit(100.0);
        assertEquals(0.10032412625983511, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savings = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savings));
        savings.deposit(2000.0);

        assertEquals(4.013968790689319, savings.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account withWithDraw = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(withWithDraw));

        Account withoutWidraw = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Adam").openAccount(withoutWidraw));

        withWithDraw.deposit(3500.0);
        withWithDraw.withdraw(500);
        //withWithDraw.printTransactions();

        withoutWidraw.deposit(3000.0);

        assertEquals(3.009723787787152, withWithDraw.interestEarned(), DOUBLE_DELTA);
        assertEquals(154.23451714066232,withoutWidraw.interestEarned(),DOUBLE_DELTA);
    }

    @Test
    public void leapyear(){
        Account quickTest = new Account(Account.MAXI_SAVINGS);
        assertFalse(quickTest.checkLeapYear(2019));
        assertTrue(quickTest.checkLeapYear(2020));
    }

    @Test
    public void checkDailyInterest(){
        Account quickTest = new Account(Account.MAXI_SAVINGS);
        Transaction quick = new Transaction(100);
        assertEquals(144426.1870535895,quickTest.dailyInterest(1000,5,quick.getDate()),DOUBLE_DELTA);
    }
}
