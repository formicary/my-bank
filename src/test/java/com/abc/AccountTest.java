package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-10;
    private Account savings;

    @Before
    public void init(){
        savings = new Account(Account.SAVINGS);
    }


    /**
     * Check that depositing and withdrawing are added as transactions in the account. There
     * should be no transactions to begin with.
     */
    @Test
    public void getTransactions(){
        assertEquals (0, savings.getTransactions().size());

        savings.deposit(100);
        assertEquals (1, savings.getTransactions().size());

        savings.withdraw(100);
        assertEquals (2, savings.getTransactions().size());
    }


    /**
     * Check that only numbers >0 can be deposited
     */
    @Test(expected = IllegalArgumentException.class)
    public void depositNegative(){
        savings.deposit(-1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void depositZero(){
        savings.deposit(0);
    }
    @Test
    public void depositPositive(){
        savings.deposit(1);
    }


    /**
     * Check that only numbers >0 can be withdrawn
     */
    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegative(){
        savings.deposit(-1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void withdrawZero(){
        savings.deposit(0);
    }
    @Test
    public void withdrawPositive(){
        savings.deposit(1);
    }


    /**
     * Check that depositing and withdrawing yields the correct amount after summing all transactions
     */
    @Test
    public void accountTotals(){
        savings.deposit(123.45);
        assertEquals(123.45, savings.sumTransactions(), DOUBLE_DELTA);

        savings.withdraw(100);
        assertEquals(23.45, savings.sumTransactions(), DOUBLE_DELTA);

        savings.withdraw(53.45);
        assertEquals(-30, savings.sumTransactions(), DOUBLE_DELTA);
    }

}
