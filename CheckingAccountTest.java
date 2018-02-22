package com.abc;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTest {

    private Bank bank;
    private Customer customer;
    private Account account;

    @org.junit.Before
    public void setup() {
        bank = new Bank();
        customer = new Customer("FirstName LastName");
        bank.getCustomers().add(customer);
        account = new CheckingAccount();
        customer.getAccounts().add(account);
    }

    // interest paid after 3 days
    @org.junit.Test
    public void interestPaidOnAccount_after3Days() {
        double result = 1000d;
        Transaction transaction = new Transaction(1000d, LocalDate.now().minusDays(3));
        account.getTransactions().add(transaction);
        for(int i = 0;i < 3;i++) {
            result += result*(0.001d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
        }
        result -= 1000d;
        assertEquals(result, account.interestPaidOnAccount(),0);
    }

    // interest paid after 0 days
    @org.junit.Test
    public void interestPaidOnAccount_after0Days() {
        Transaction transaction = new Transaction(1000d, LocalDate.now());
        account.getTransactions().add(transaction);
        assertEquals(0d, account.interestPaidOnAccount(),0);
    }

    // interest paid if no transaction was made
    @org.junit.Test
    public void interestPaidOnAccount_noTransactionSoFar () {
        assertEquals(0d, account.interestPaidOnAccount(),0);
    }

    // all transactions after 3 days including interest earnings
    @org.junit.Test
    public void buildTransactions_after3Days() {
        account.getTransactions().add(new Transaction(1000d, LocalDate.now().minusDays(3)));
        double newAmount=1000d, balance=1000d;
        LocalDate newDate;

        for(int i = 0; i < 4; i++) {
            newDate = LocalDate.now().minusDays(3 - i);
            assertEquals(newAmount, account.buildTransactions().get(i).getAmount(),0);
            assertEquals(newDate, account.buildTransactions().get(i).getDate());
            newAmount = balance*(0.001/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            balance = newAmount + balance;
        }
    }

    // all transactions after 3 days including interest earnings, two consecutive transactions at the same day (no interest is earned in between)
    @org.junit.Test
    public void buildTransactions_after3Days_2transactionsAtTheSameDay() {
        account.getTransactions().add(new Transaction(1000d, LocalDate.now().minusDays(3)));
        account.getTransactions().add(new Transaction(500d, LocalDate.now().minusDays(3)));
        double newAmount, balance=1500d;
        LocalDate newDate;

        newAmount = 1000d;
        newDate = LocalDate.now().minusDays(3);
        assertEquals(newAmount, account.buildTransactions().get(0).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(0).getDate());

        newAmount = 500d;
        assertEquals(newAmount, account.buildTransactions().get(1).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(1).getDate());

        newAmount = balance * (0.001d / (LocalDate.now().minusDays(3).isLeapYear() ? 366 : 365));
        balance = newAmount + balance;
        newDate = LocalDate.now().minusDays(2);
        assertEquals(newAmount, account.buildTransactions().get(2).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(2).getDate());

        newAmount = balance * (0.001d / (LocalDate.now().minusDays(2).isLeapYear() ? 366 : 365));
        balance = newAmount + balance;
        newDate = LocalDate.now().minusDays(1);
        assertEquals(newAmount, account.buildTransactions().get(3).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(3).getDate());

        newAmount = balance * (0.001d / (LocalDate.now().minusDays(1).isLeapYear() ? 366 : 365));
        balance = newAmount + balance;
        newDate = LocalDate.now().minusDays(0);
        assertEquals(newAmount, account.buildTransactions().get(4).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(4).getDate());
    }

    // all transactions when no transaction was made
    @org.junit.Test
    public void buildTransactions_noTransactionsSoFar() {
        assertEquals(0, account.buildTransactions().size(),0);
    }
}