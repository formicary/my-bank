package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testCustomerStatement(){
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry", "henry@email.com").addAccount(checkingAccount).addAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                DateProvider.now().toString() +  " Deposit: $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                DateProvider.now().toString() +  " Deposit: $4,000.00\n" +
                DateProvider.now().toString() +  " Withdrawal: $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts: $3,900.00", henry.getTotalStatement());
    }
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar", "oscar@email.com").addAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }
    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar", "oscar@email.com")
                .addAccount(new SavingsAccount());
        oscar.addAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar", "oscar@email.com")
                .addAccount(new SavingsAccount());
        oscar.addAccount(new CheckingAccount());
        oscar.addAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    @Test
    public void testTransferBetweenAccounts(){
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Customer oscar = new Customer("Oscar", "oscar@email.com").
                addAccount(checkingAccount).addAccount(savingsAccount);
        checkingAccount.deposit(100);
        oscar.transferBetweenAccounts(checkingAccount, savingsAccount, 50);
        assertEquals(50, checkingAccount.getAccountBalance(), DOUBLE_DELTA);
        assertEquals(50, savingsAccount.getAccountBalance(), DOUBLE_DELTA);
    }
}
