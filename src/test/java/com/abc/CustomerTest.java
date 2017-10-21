package com.abc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer husam = new Customer("Husam").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Husam\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", husam.getStatement());
    }

    @Test
    public void testTransfer(){
        Account checking = new Account(Account.CHECKING);
        Account savings = new Account(Account.SAVINGS);
        Account maxi_savings = new Account(Account.MAXI_SAVINGS);
        Customer jamal = new Customer("Jamal");

        jamal.openAccount(checking);
        jamal.openAccount(savings);
        jamal.openAccount(maxi_savings);

        checking.deposit(300.0);
        savings.deposit(2000.0);
        maxi_savings.deposit(3500.0);

        jamal.transfer(0, 1, 300.0);
        assertEquals(0.0, checking.getMoney(), DOUBLE_DELTA);
        assertEquals(2300.0, savings.getMoney(), DOUBLE_DELTA);


        jamal.transfer(1, 2, 500.0);
        assertEquals(1800.0, savings.getMoney(), DOUBLE_DELTA);
        assertEquals(4000.0, maxi_savings.getMoney(), DOUBLE_DELTA);

    }

    @Test
    public void testOneAccount(){
        Customer chris = new Customer("Chris").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, chris.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer mike = new Customer("Mike")
                .openAccount(new Account(Account.SAVINGS));
        mike.openAccount(new Account(Account.CHECKING));
        assertEquals(2, mike.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer eli = new Customer("Eli")
                .openAccount(new Account(Account.SAVINGS));
        eli.openAccount(new Account(Account.CHECKING));
        eli.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, eli.getNumberOfAccounts());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void depositTester() {
        Account account = new Account(0);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("amount must be greater than zero");

        account.deposit(-50);

    }

    @Test
    public void withdrawTester() {
        Account account = new Account(0);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("amount must be greater than zero");

        account.withdraw(-50);

    }

    @Test
    public void withdrawNonExistingFunds() {
        Account account = new Account(0);
        account.deposit(300);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Insufficient funds");

        account.withdraw(350);

    }

    @Test
    public void transferNonExistingFundsTest(){
        Account savingsAccount = new Account(0);
        Account checkingAccount = new Account(1);

        savingsAccount.deposit(250);
        Customer husam = new Customer("Husam");

        husam.openAccount(savingsAccount);
        husam.openAccount(checkingAccount);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Insufficient funds");


        husam.transfer(0, 1, 350);
    }

    @Test
    public void transferWithInsufficientAccounts(){
        Account savingsAccount = new Account(0);

        savingsAccount.deposit(250);
        Customer husam = new Customer("Husam");

        husam.openAccount(savingsAccount);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("You must have at least two accounts for this transaction");


        husam.transfer(0, 1, 200);
    }
}
