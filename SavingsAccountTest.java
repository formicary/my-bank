package com.abc;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {

    private Bank bank;
    private Customer customer;
    private Account account;

    @org.junit.Before
    public void setup() {
        bank = new Bank();
        customer = new Customer("FirstName LastName");
        bank.getCustomers().add(customer);
        account = new SavingsAccount();
        customer.getAccounts().add(account);
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

    // interest paid after 3 days, balance lower than 1000
    @org.junit.Test
    public void interestPaidOnAccount_after3Days_balanceLow() {
        double result = 500d;
        Transaction transaction = new Transaction(500d, LocalDate.now().minusDays(3));
        account.getTransactions().add(transaction);
        for(int i = 0;i < 3;i++) {
            result += result*(0.001d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
        }
        result -= 500d;
        assertEquals(result, account.interestPaidOnAccount(),0);
    }

    // interest paid after 3 days, balance higher than 1000
    @org.junit.Test
    public void interestPaidOnAccount_after3Days_balanceHigh() {
        double result = 1500d;
        Transaction transaction = new Transaction(1500d, LocalDate.now().minusDays(3));
        account.getTransactions().add(transaction);
        for(int i = 0;i < 3;i++) {
            result += result*(0.002d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
        }
        result -= 1500d;
        assertEquals(result, account.interestPaidOnAccount(),0);
    }

    // interest paid after 3 days, balance starts lower than 1000 and gets higher than 1000 in between
    @org.junit.Test
    public void interestPaidOnAccount_after3Days_balanceLowToHighTransition() {
        double result = 1000d;
        Transaction transaction = new Transaction(1000d, LocalDate.now().minusDays(3));
        account.getTransactions().add(transaction);
        for(int i = 0;i < 3;i++) {
            if(result > 1000) {
                result += result*(0.002d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            }else {
                result += result*(0.001d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            }
        }
        result -= 1000d;
        assertEquals(result, account.interestPaidOnAccount(),0);
    }

    // interest paid after 3 days, balance starts higher than 1000 and gets lower than 1000 in between
    @org.junit.Test
    public void interestPaidOnAccount_after3Days_balanceHighToLowTransition() {
        double result = 1100d;
        Transaction transaction = new Transaction(1100d, LocalDate.now().minusDays(5));
        Transaction transaction2 = new Transaction(-200d, LocalDate.now().minusDays(3));
        account.getTransactions().add(transaction);
        account.getTransactions().add(transaction2);
        for(int i = 0;i < 2;i++) {
            if(result > 1000) {
                result += result*(0.002d/(LocalDate.now().minusDays(5 - i).isLeapYear() ? 366 : 365));
            }else {
                result += result*(0.001d/(LocalDate.now().minusDays(5 - i).isLeapYear() ? 366 : 365));
            }
        }
        result -= 200d;
        for(int i = 0;i < 3;i++) {
            if(result > 1000) {
                result += result*(0.002d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            }else {
                result += result*(0.001d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            }
        }
        result -=900;
        assertEquals(result, account.interestPaidOnAccount(),0);
    }

    // all transactions after 3 days including interest earnings, balance lower than 1000
    @org.junit.Test
    public void buildTransactions_after3Days_balanceLow() {
        account.getTransactions().add(new Transaction(500d, LocalDate.now().minusDays(3)));
        double newAmount = 500d, balance = 500d;
        LocalDate newDate;

        for(int i = 0; i < 4; i++) {
            newDate = LocalDate.now().minusDays(3 - i);
            assertEquals(newAmount, account.buildTransactions().get(i).getAmount(),0);
            assertEquals(newDate, account.buildTransactions().get(i).getDate());
            newAmount = balance*(0.001/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            balance = newAmount + balance;
        }
    }

    // all transactions after 3 days including interest earnings, balance higher than 1000
    @org.junit.Test
    public void buildTransactions_after3Days_balanceHigh() {
        account.getTransactions().add(new Transaction(1500d, LocalDate.now().minusDays(3)));
        double newAmount = 1500d, balance = 1500d;
        LocalDate newDate;

        for(int i = 0; i < 4; i++) {
            newDate = LocalDate.now().minusDays(3 - i);
            assertEquals(newAmount, account.buildTransactions().get(i).getAmount(),0);
            assertEquals(newDate, account.buildTransactions().get(i).getDate());
            newAmount = balance*(0.002/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            balance = newAmount + balance;
        }
    }

    // all transactions after 3 days including interest earnings, starts lower than 1000 and gets higher than 1000 in between
    @org.junit.Test
    public void buildTransactions_after3Days_balanceLowToHighTransition() {
        account.getTransactions().add(new Transaction(1000d, LocalDate.now().minusDays(3)));
        double newAmount = 1000d, balance = 1000d;
        LocalDate newDate;
        double interestLow = 0.001d;
        double interestHigh = 0.002d;
        for(int i = 0; i < 4; i++) {
            newDate = LocalDate.now().minusDays(3 - i);
            assertEquals(newAmount, account.buildTransactions().get(i).getAmount(),0);
            assertEquals(newDate, account.buildTransactions().get(i).getDate());
            if(balance > 1000) {
                newAmount = balance*(interestHigh/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            }else {
                newAmount = balance*(interestLow/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
            }
            balance = newAmount + balance;
        }
    }

    // all transactions after 3 days including interest earnings, two consecutive transactions at the same day (no interest is earned in between), resulting balance lower than 1000
    @org.junit.Test
    public void buildTransactions_after3Days_2transactionsAtTheSameDay_balanceLow() {
        account.getTransactions().add(new Transaction(400d, LocalDate.now().minusDays(3)));
        account.getTransactions().add(new Transaction(500d, LocalDate.now().minusDays(3)));
        double newAmount, balance=900d;
        LocalDate newDate;

        newAmount = 400d;
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

    // all transactions after 3 days including interest earnings, two consecutive transactions at the same day (no interest is earned in between), resulting balance higher than 1000
    @org.junit.Test
    public void buildTransactions_after3Days_2transactionsAtTheSameDay_balanceHigh() {
        account.getTransactions().add(new Transaction(800d, LocalDate.now().minusDays(3)));
        account.getTransactions().add(new Transaction(500d, LocalDate.now().minusDays(3)));
        double newAmount, balance=1300d;
        LocalDate newDate;

        newAmount = 800d;
        newDate = LocalDate.now().minusDays(3);
        assertEquals(newAmount, account.buildTransactions().get(0).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(0).getDate());

        newAmount = 500d;
        assertEquals(newAmount, account.buildTransactions().get(1).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(1).getDate());

        newAmount = balance * (0.002d / (LocalDate.now().minusDays(3).isLeapYear() ? 366 : 365));
        balance = newAmount + balance;
        newDate = LocalDate.now().minusDays(2);
        assertEquals(newAmount, account.buildTransactions().get(2).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(2).getDate());

        newAmount = balance * (0.002d / (LocalDate.now().minusDays(2).isLeapYear() ? 366 : 365));
        balance = newAmount + balance;
        newDate = LocalDate.now().minusDays(1);
        assertEquals(newAmount, account.buildTransactions().get(3).getAmount(),0);
        assertEquals(newDate, account.buildTransactions().get(3).getDate());

        newAmount = balance * (0.002d / (LocalDate.now().minusDays(1).isLeapYear() ? 366 : 365));
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