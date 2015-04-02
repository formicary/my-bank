package com.abc;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5.0);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void check_last_ten_days_transactions() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = sdf.parse("21/03/2015 15:31:23");

        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer manos = new Customer("Manos").openAccount(maxiSavingsAccount);
        double amount = 5000;
        maxiSavingsAccount.specificDeposit(amount, date);

        assertEquals(manos.getTotalInterestEarned(), maxiSavingsAccount.getSumTransactions() * 0.05, 0.0);
    }
}
