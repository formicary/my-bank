package com.abc;

import com.abc.accounts.SavingsAccount;
import com.abc.history.TransactionHistory;
import static org.junit.Assert.*;

import com.abc.utilities.Money;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by vahizan on 21/08/2017.
 */
public class TransactionHistoryTest {
    private TransactionHistory history;

    @Before
    public void setUp(){
        history=new TransactionHistory();
    }
    @Test
    public void dontGetWithdrawalDateIfNoWithdrawalsAreMade(){
        Date date = history.lastWithdrawal();
    }
    @Test
    public void getCorrectWithdrawalDateIfWithdrawalsAreMade(){
        SavingsAccount account =new SavingsAccount();
        Money moneyDeposit = new Money(1000);
        Money moneyWithdraw = new Money(100);
        account.depositMoney(moneyDeposit);
        account.withdrawMoney(moneyWithdraw);
        Date date = history.lastWithdrawal();
        System.out.println(date.toString());
        account.withdrawMoney(moneyWithdraw);
        System.out.println(date.toString());
        assertEquals(new Date().toString(),date.toString());
    }
}
