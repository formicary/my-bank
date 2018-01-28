package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testInvalidAccountType(){
        Account account = new Account(5);
        assertEquals(0, account.getAccountType());
    }

    @Test //Test customer statement generation
    public void testWithdrawOverLimit(){
        Bank bank = new Bank();

        Customer henry = new Customer("Henry", bank);
        Account checkingAccount = henry.openAccount(Account.CHECKING);

        checkingAccount.deposit(100.0);
        try {
            checkingAccount.withdraw(150.0);
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        assertEquals(100,checkingAccount.getBalance(),DOUBLE_DELTA);

    }

    @Test
    public void testWithdrawLessThanOne(){
        Bank bank = new Bank();

        Customer henry = new Customer("Henry", bank);
        Account checkingAccount = henry.openAccount(Account.CHECKING);

        checkingAccount.deposit(100.00);
        for(int i =0;i<100;i++){
            checkingAccount.withdraw(0.01);
        }
        System.out.println(henry.getStatement());
    }

}
