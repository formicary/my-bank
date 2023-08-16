package com.abc.DateUtilsTests;

import org.junit.Test;

import com.abc.DateUtils.DateChecker;
import static org.junit.Assert.assertTrue;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.Transaction;
import java.util.Date;
import java.util.List;

public class DateCheckerTest {
    @Test
    public void testIsWithinLast10Days() {
        DateChecker check = new DateChecker();
        Transaction t = new Transaction(5);
        Date transactionDate = t.getTransactionDate();
        assertTrue(check.isWithinLast10Days(transactionDate));
    }

    @Test
    public void testHasTransactionsWithinLastTenDays() {
        Bank bank = new Bank();
        DateChecker dateCheck = new DateChecker();
        Account maxiSaverAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSaverAccount));
        for (int i = 0; i < 5; i++) {
            maxiSaverAccount.deposit(3000.0);
        }
        maxiSaverAccount.withdraw(100);
        List<Transaction> transactions = maxiSaverAccount.getTransactionList();
        assertTrue(dateCheck.hasTransactionsWithinLastTenDays(transactions));
    }
}
