package com.abc;

import com.abc.ENUMS.AccountType;
import com.abc.ENUMS.TransactionType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;
    Customer oscar;
    Account checkingAccount;
    Account savingAccount;
    Account maxiSavingAccount;

    @Before
    public void init()
    {
        oscar=new Customer("Oscar");
        checkingAccount=new Account(AccountType.CHECKING);
        savingAccount=new Account(AccountType.SAVINGS);
        maxiSavingAccount=new Account(AccountType.MAXI_SAVINGS);

    }

    @Test //Test customer statement generation
    public void getStatementTest(){
        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingAccount);
        checkingAccount.deposit(100.0,TransactionType.DEPOSIT);
        savingAccount.deposit(4000.0,TransactionType.DEPOSIT);
        savingAccount.withdraw(200.0, TransactionType.WITHDRAWL);
        savingAccount.withdraw(20,TransactionType.FUND_TRANSFER_OWN_ACCOUNT);

        assertEquals("Statement for Oscar\n" +
                "\n" +
                "Checking Account\n" +
                "  DEPOSIT $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  DEPOSIT $4,000.00\n" +
                "  WITHDRAWL $200.00\n" +
                "  FUND_TRANSFER_OWN_ACCOUNT $20.00\n" +
                "Total $3,780.00\n" +
                "\n" +
                "Total In All Accounts $3,880.00", oscar.getStatement());
    }

    @Test
    public void testOneAccount(){
       oscar.openAccount(savingAccount);
       assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        oscar.openAccount(savingAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
       oscar.openAccount(savingAccount);
       oscar.openAccount(checkingAccount);
       oscar.openAccount(maxiSavingAccount);
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void transferMoneyBetweenOwnAccountTest()  {
        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingAccount);
        checkingAccount.deposit(500.00,TransactionType.DEPOSIT);
        savingAccount.deposit(200.00,TransactionType.DEPOSIT);
       boolean done= oscar.transferMoneyBetweenOwnAccount(checkingAccount,savingAccount,100);
       assertEquals(true,done);
       assertEquals(savingAccount.getBalance(),300,DOUBLE_DELTA);
        assertEquals(checkingAccount.getBalance(),400,DOUBLE_DELTA);
        assertEquals(2,checkingAccount.transactions.size());
        assertEquals(2,savingAccount.transactions.size());



    }
    @Test(expected = IllegalStateException.class)
    public void transferMoneyBetweenOwnAccountWithInsufficientFundTest()  {
       oscar.openAccount(savingAccount);
       oscar.openAccount(checkingAccount);
        checkingAccount.deposit(100.00,TransactionType.DEPOSIT);
        savingAccount.deposit(200.00,TransactionType.DEPOSIT);
        boolean done= oscar.transferMoneyBetweenOwnAccount(checkingAccount,savingAccount,300);

    }
    @Test(expected = IllegalArgumentException.class)
    public void transferMoneyBetweenOwnAccountWithNegativeTransferAmountTest()  {
       oscar.openAccount(savingAccount);
       oscar.openAccount(checkingAccount);
        checkingAccount.deposit(100.00,TransactionType.DEPOSIT);
        savingAccount.deposit(200.00,TransactionType.DEPOSIT);
        boolean done= oscar.transferMoneyBetweenOwnAccount(checkingAccount,savingAccount,-300);

    }
    @Test(expected = IllegalArgumentException.class)
    public void transferMoneyBetweenOwnAccountWithZeroTransferAmountTest()  {
       oscar.openAccount(savingAccount);
       oscar.openAccount(checkingAccount);
        checkingAccount.deposit(100.00,TransactionType.DEPOSIT);
        savingAccount.deposit(200.00,TransactionType.DEPOSIT);
        boolean done= oscar.transferMoneyBetweenOwnAccount(checkingAccount,savingAccount,0);


    }
    @Test(expected = IllegalStateException.class)
    public void transferMoneyIfCustomerDoesNotOwnTheAccountTest()  {
       oscar.openAccount(savingAccount);
       oscar.openAccount(checkingAccount);
        checkingAccount.deposit(100.00,TransactionType.DEPOSIT);
        savingAccount.deposit(200.00,TransactionType.DEPOSIT);
        boolean done= oscar.transferMoneyBetweenOwnAccount(checkingAccount,maxiSavingAccount,200);


    }
}
