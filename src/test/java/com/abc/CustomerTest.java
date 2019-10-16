package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomerTest {
    private Account checking;
    private Account saving;
    private Customer mo;

    @Before
    public void before() {
        checking = new CheckingAccount();
        saving = new SavingAccount();
        mo = new Customer("Mohammed", checking);
        mo.openAccount(saving);
    }

    @Test //Test customer statement generation
    public void getStatement() {

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingAccount();

        Customer henry = new Customer("Henry", checkingAccount)
                .openAccount(savingsAccount);

        checkingAccount.deposit(Transaction.ONE_HUNDRED);
        savingsAccount.deposit(new BigDecimal("4000.00"));
        savingsAccount.withdraw(Transaction.TWO_HUNDRED);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit £100.00\n" +
                "Total £100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit £4,000.00\n" +
                "  withdrawal £200.00\n" +
                "Total £3,800.00\n" +
                "\n" +
                "Total In All Accounts £3,900.00", henry.getStatement());
    }

    @Test //Test customer statement generation
    public void getStatement2() {

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingAccount();
        Account maxiAccount = new MaxiAccount();

        Customer bob = new Customer("Bob", checkingAccount)
                .openAccount(savingsAccount)
                .openAccount(maxiAccount);

        checkingAccount.deposit(Transaction.ONE_HUNDRED);
        checkingAccount.deposit(Transaction.FIFTY);
        checkingAccount.deposit(Transaction.TEN);
        checkingAccount.withdraw(new BigDecimal("55.50"));
        savingsAccount.deposit(new BigDecimal("3000.00"));
        savingsAccount.withdraw(Transaction.ONE_HUNDRED);
        maxiAccount.deposit(new BigDecimal("5000.00"));
        maxiAccount.deposit(new BigDecimal("1000.00"));

        assertEquals("Statement for Bob\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit £100.00\n" +
                "  deposit £50.00\n" +
                "  deposit £10.00\n" +
                "  withdrawal £55.50\n" +
                "Total £104.50\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit £3,000.00\n" +
                "  withdrawal £100.00\n" +
                "Total £2,900.00\n" +
                "\n" +
                "Maxi Saving Account\n" +
                "  deposit £5,000.00\n" +
                "  deposit £1,000.00\n" +
                "Total £6,000.00\n" +
                "\n" +
                "Total In All Accounts £9,004.50", bob.getStatement());
    }

    @Test (expected = IllegalArgumentException.class)
    public void incorrectName() {
        Customer oscar = new Customer(" Oscar", new SavingAccount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void incorrectName2() {
        Customer oscar = new Customer("Oscar ", new SavingAccount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void incorrectName3() {
        Customer oscar = new Customer("Osc ar", new SavingAccount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void incorrectName4() {
        Customer oscar = new Customer("Oscar1", new SavingAccount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void longName() {
        Customer Nathanielilop = new Customer("Nathanielilop", new SavingAccount());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar", new SavingAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar", new SavingAccount())
                .openAccount(new SavingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccount() {
        Customer oscar = new Customer("Oscar", new SavingAccount())
                .openAccount(new CheckingAccount())
                .openAccount(new CheckingAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test   //Test getting a customers account via acc num
    public void getRealAccount() {
        checking.deposit(Transaction.ONE_HUNDRED);
        saving.deposit(new BigDecimal("500.00"));
        String acc = saving.getAccountNum();

        assertEquals("Savings Account", mo.getAccount(acc).getAccountName());
        assertEquals("500.00", mo.getAccount(acc).getBalance().toString());
    }

    @Test
    public void getFakeAccount() {
        checking.deposit(Transaction.TEN);
        saving.deposit(new BigDecimal("250.00"));
        String acc = "3Mfgs4gX";    //Fake account number

        assertNull(mo.getAccount(acc));
    }

    @Test
    public void normalTransfer() {
        String checkAcc = checking.getAccountNum();
        String savAcc = saving.getAccountNum();

        checking.deposit(Transaction.TEN);
        saving.deposit(new BigDecimal("250.00"));
        //before transfer
        assertEquals("10.00", mo.getAccount(checkAcc).getBalance().toString());
        assertEquals("250.00", mo.getAccount(savAcc).getBalance().toString());

        //after transfer
        mo.transfer(savAcc, checkAcc, Transaction.FIFTY);
        assertEquals("60.00", mo.getAccount(checkAcc).getBalance().toString());
        assertEquals("200.00", mo.getAccount(savAcc).getBalance().toString());
    }

    @Test
    public void incorrectTransfer() {
        String checkAcc = checking.getAccountNum();
        String savingAcc = "aGrv4Vs7"; //fake account number

        checking.deposit(Transaction.TEN);
        saving.deposit(new BigDecimal("250.00"));
        //before transfer
        assertEquals("10.00", checking.getBalance().toString());
        assertEquals("250.00", saving.getBalance().toString());

        //transfer to incorrect account
        mo.transfer(savingAcc, checkAcc, Transaction.FIFTY);
        assertEquals("10.00", checking.getBalance().toString());
        assertEquals("250.00", saving.getBalance().toString());
    }
}
