package com.abc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;
    private Date date;
    private Customer henry;

    @Before
    public void setUp(){
        date = DateProvider.getInstance().now();
        henry = new Customer("Henry");
    }

    @Test
    public void getStatementTest(){

        Account checkingAccount = new CheckingAccount(UUID.randomUUID());
        Account savingsAccount = new SavingsAccount(UUID.randomUUID());

        henry.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0, date);
        savingsAccount.deposit(4000.0, date);
        savingsAccount.withdraw(200.0 ,date);
        henry.transfer(savingsAccount.getAccountID(), checkingAccount.getAccountID(),1200.0, date);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $1,200.00\n" +
                "Total $1,300.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  withdrawal $1,200.00\n" +
                "Total $2,600.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void transferTest(){
        Account checkingAccount = new CheckingAccount(UUID.randomUUID());
        Account savingsAccount = new SavingsAccount(UUID.randomUUID());

        henry.openAccount(checkingAccount).openAccount(savingsAccount);

        savingsAccount.deposit(3000.0, date);
        checkingAccount.deposit(1000.0, date);
        henry.transfer(savingsAccount.getAccountID(), checkingAccount.getAccountID(), 500.0, date);

        assertEquals(1500, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void getNumberOfAccountsTest(){
        int n = 10;
        for(int i = 0; i < n; i++){
            if(i >= 0 && i < 4){
                henry.openAccount(new CheckingAccount(UUID.randomUUID()));
            }
            else if(i >= 4 && i < 7){
                henry.openAccount(new SavingsAccount(UUID.randomUUID()));
            }
            else{
                henry.openAccount(new MaxiSavingsAccount(UUID.randomUUID()));
            }
        }
        assertEquals(n, henry.getNumberOfAccounts());
    }

    @Test
    public void getAccountTest(){
        UUID uuid = UUID.randomUUID();

        CheckingAccount checkingAccount = new CheckingAccount(uuid);
        henry.openAccount(checkingAccount);

        assertEquals(checkingAccount, henry.getAccount(uuid));
    }

    @Test
    public void getAccount_AccountNotFound_Test() {
        UUID uuid = UUID.randomUUID();
        try{
            henry.getAccount(uuid);
        }
        catch (RuntimeException e){
            String message = e.getMessage();
            assertEquals(String.format("account: %1$s does not exist", uuid.toString()), message);
        }
    }

    @Test
    public void totalInterestEarnedTest(){
        Account checkingAccount = new CheckingAccount(UUID.randomUUID());
        Account savingsAccount = new SavingsAccount(UUID.randomUUID());

        checkingAccount.deposit(2000.0, date);
        savingsAccount.deposit(3000.0, date);

        henry.openAccount(checkingAccount).openAccount(savingsAccount);

        assertEquals(7.0, henry.totalInterestEarned(Account.YEARLY), DOUBLE_DELTA);
    }
}
