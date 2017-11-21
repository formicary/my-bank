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
    private Customer a;
    private Bank bank
    private Account aMaxiSaving;
    private List<Transaction> aCheckingTransaction;

    @Before

    public void setUp()
    {
       bank = new Bank;
       a = new Customer("A");
       bank.addCustomer(a);
       aChecking = new CheckingAccount();
       a.openAccount(aChecking);
       aCheckingTransaction = aChecking.getTransactions();
    }
    @Test
    public void WithdrawSufficientTest()
    {
        aChecking.deposit(450);
        aChecking.withdraw(100)
        assertTrue(350 == aChecking.getBalance());
    }


}
