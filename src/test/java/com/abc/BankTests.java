package com.abc;

import static org.junit.Assert.assertEquals;

import com.abc.accounts.AccountType;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class BankTests {

    private Bank bank;

    @Before
    public void setUp() {

        bank = new Bank();
        bank.newCustomer("Waqas", 101);
        bank.newCustomer("Jordan", 102);

        Customer waqas = bank.getCustomer(101);
        Customer jordan = bank.getCustomer(102);

        waqas.openAccount(AccountType.Checking, 1451894);
        waqas.openAccount(AccountType.Savings, 1451895);

        jordan.openAccount(AccountType.Checking, 1451894);
        jordan.openAccount(AccountType.MaxiSavings, 1451895);


    }

    @Test
    public void customerSummary() {

       String expectedSummary = "Bank Customers:\n" +
               " - 101 Waqas [Number of accounts: 2]\n" +
               " - 102 Jordan [Number of accounts: 2]";

       assertEquals(expectedSummary, bank.customerSummary());
    }

    @Test
    public void totalInterestPaid() {

        Customer waqas = bank.getCustomer(101);
        Customer jordan = bank.getCustomer(102);

        waqas.getAccount(1451894).depositAmount(10000.00, "Deposit");
        waqas.getAccount(1451895).depositAmount(5000.00, "Deposit");

        jordan.getAccount(1451894).depositAmount(10000.00, "Deposit");
        jordan.getAccount(1451895).depositAmount(5000.00, "Deposit");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.JANUARY, 1, 0, 0, 0);
        Date date = calendar.getTime();
        //Set the transaction dates to 1/01/2019 and calculate interest until now
        waqas.getAccount(1451894).getTransactions().get(0).setTransactionDate(date);
        waqas.getAccount(1451895).getTransactions().get(0).setTransactionDate(date);
        jordan.getAccount(1451894).getTransactions().get(0).setTransactionDate(date);
        jordan.getAccount(1451895).getTransactions().get(0).setTransactionDate(date);

        //This function has already been tested in CustomersTests
        double expectedInterestPaid = Math.round((waqas.totalInterestEarned() + jordan.totalInterestEarned())*100.00)/100.00;

        assertEquals(expectedInterestPaid, bank.totalInterestPaid(), 0.001);
    }

}
