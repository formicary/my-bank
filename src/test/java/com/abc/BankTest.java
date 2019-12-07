package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-9;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.addAccount(0);
        assertEquals("\nCustomer: John" + "\nAccounts: 1", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.addAccount(0);
        bank.addCustomer(bill);
        bill.deposit(100.0, 0);

        assertEquals(0.0002737850787, bank.interestSummary(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.addAccount(1);
        bill.deposit(1500, 1);
        assertEquals(8 / 1461, bank.interestSummary(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.addAccount(2);
        bill.deposit(3000, 2);
        assertEquals(20 / 487, bank.interestSummary(), DOUBLE_DELTA);
    }

    @Test //Test customer statement generation
    public void testApp() {

        Customer henry = new Customer("Henry");
        henry.addAccount(0);
        henry.addAccount(1);
        henry.deposit(100.0, 0);
        henry.deposit(4000.0, 1);
        henry.withdraw(200.0, 1);
        henry.transfer(0, 1, 100);
        henry.getStatement();
        assertEquals(" Deposit of 100.0 concerning account type 0\nDeposit of 4000.0 "
                + "concerning account type 1\nWithdrawal of 200.0 concerning account type "
                + "1\nDeposit of 100.0 concerning account type 1\n"
                + "Total Balance: 4000.0", henry.getStatement());




        
     
    }

}
