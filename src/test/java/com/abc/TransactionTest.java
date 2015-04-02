package com.abc;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2015, Calendar.MARCH, 21, 10, 11, 12);
        Date date = cal1.getTime();

        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer manos = new Customer("Manos").openAccount(maxiSavingsAccount);
        double amount = 5000;
        maxiSavingsAccount.specificDeposit(amount, date);

        assertEquals(manos.getTotalInterestEarned(), maxiSavingsAccount.getSumTransactions() * 0.05, 0.0);
    }
}

