package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer jack = new Customer("Jack");
        Customer bill = new Customer("Bill");
        Customer emily = new Customer("Emily");

        bank.addCustomer(john);
        bank.addCustomer(jack);
        bank.addCustomer(bill);
        bank.addCustomer(emily);

        Account johnCheckingAccount = new Account(Account.CHECKING);
        Account jackSavingsAccount = new Account(Account.SAVINGS);
        Account jackCheckingAccount = new Account(Account.CHECKING);
        Account billMaxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Account emilyCheckingAccount = new Account(Account.CHECKING);

        john.openAccount(johnCheckingAccount);
        jack.openAccount(jackSavingsAccount);
        jack.openAccount(jackCheckingAccount);
        bill.openAccount(billMaxiSavingsAccount);
        emily.openAccount(emilyCheckingAccount);

        johnCheckingAccount.deposit(450.54);
        jackSavingsAccount.deposit(80.95);
        jackCheckingAccount.deposit(620.31);
        billMaxiSavingsAccount.deposit(300);
        emilyCheckingAccount.deposit(110.90);

        assertEquals("Customer Summary\n\n - John (1 account) - Statement for John" + "\n" +
                "Checking Account\n" + 
                "  deposit : $450.54\r\n" + 
                "Total : $450.54\r\n" + 
                "\r\n" + 
                "Total Of John's Accounts : $450.54\r\n" + 
                "\r\n" + 
                " - Jack (2 accounts) - Statement for Jack\n" + 
                "Savings Account\r\n" + 
                "  deposit : $80.95\r\n" + 
                "Total : $80.95\r\n" + 
                "Checking Account\r\n" + 
                "  deposit : $620.31\r\n" + 
                "Total : $620.31\r\n" + 
                "\r\n" + 
                "Total Of Jack's Accounts : $701.26\r\n" + 
                "\r\n" + 
                " - Bill (1 account) - Statement for Bill\n" +  
                "Maxi Savings Account\r\n" + 
                "  deposit : $300.00\r\n" + 
                "Total : $300.00\r\n" + 
                "\r\n" + 
                "Total Of Bill's Accounts : $300.00\r\n" + 
                "\r\n" + 
                " - Emily (1 account) - Statement for Emily\n" + 
                "Checking Account\r\n" + 
                "  deposit : $110.90\r\n" + 
                "Total : $110.90\r\n" + 
                "\r\n" + 
                "Total Of Emily's Accounts : $110.90" + 
                "\n\nTotal Of All Accounts : $1,562.70"
                , bank.customerSummary());
    }
 
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(49.58);
        checkingAccount.deposit(76.25);

        assertEquals(0.13, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);
        savingsAccount.withdraw(746.85);
        savingsAccount.deposit(1050.67);

        assertEquals(2.61, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(385.69);
        maxiSavingsAccount.deposit(480.71);

        assertEquals(179.50, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    //Test interest rate earned via maxi_savings according to no withdrawal for 10 days prior to today
    @Test
    public void maxi_savings_no_withdrawal() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(100);
        maxiSavingsAccount.withdraw(50);

        /*Get size of transactions list then set withdrawDateInMilliseconds to 10 days prior to recorded
        transaction date*/
        int sizeOfTransactions = maxiSavingsAccount.transactions.size();
        long withdrawDateInMilliseconds = maxiSavingsAccount.transactions.get(sizeOfTransactions - 1)
        .transactionDate.getTime() - 864000000;
        //Set transaction date to Date type of withdrawDateInMilliseconds
        maxiSavingsAccount.transactions.get(sizeOfTransactions - 1).transactionDate = new Date(withdrawDateInMilliseconds);
        
        assertEquals(2.50, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void getFirstAndLastCustomer() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("Bill"));
        bank.addCustomer(new Customer("Jack"));
        bank.addCustomer(new Customer("Emily"));

        assertTrue("Get first customer : unexpected name", bank.getFirstCustomer().equals("Bill"));
        assertTrue("Get last customer : unexpected name", bank.getLastCustomer().equals("Emily"));
    }
}