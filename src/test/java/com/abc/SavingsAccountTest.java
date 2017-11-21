package com.abc;

import com.abc.Account;
import com.abc.Bank;
import com.abc.SavingsAccount;
import com.abc.Customer;
import com.abc.DateProvider;
import com.abc.Transaction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.Before;

public class SavingAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;
    private Customer a;
    private Account aSaving;
    private List<Transaction> aSavingTransaction;

    @Before

    public void setUp()
    {
       bank = new Bank;
       a = new Customer("A");
       bank.addCustomer(a);
       aSaving = new SavingsAccount();
       a.openAccount(aSaving);
       aSavingTransaction = aSaving.getTransactions();
    }
    @Test
    public void savingsDepositTest()
    {
        aSaving.deposit(400);
        assertTrue(400 == aSaving.getBalance());
    }


}
