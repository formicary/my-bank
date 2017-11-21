package com.abc;

import com.abc.Account;
import com.abc.Bank;
import com.abc.MaxiSavingsAccount;
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

public class CheckingAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;
    private Customer a;
    private Account aMaxiSavings;
    private List<Transaction> aMaxiSavingsTransaction;

    @Before

    public void setUp()
    {
       bank = new Bank;
       aMaxiSavings = new MaxiSavingsAccount();
       a = new Customer("A");
       bank.addCustomer(a);
       a.openAccount(aMaxiSavings);
       aMaxiSavingsTransaction = aMaxiSavings.getTransactions();
    }
    @Test
    public void MaxiSavingsDepositTest()
    {
        aMaxiSavings.deposit(500);
        assertTrue(500 == aMaxiSavings.getBalance());
    }
}
