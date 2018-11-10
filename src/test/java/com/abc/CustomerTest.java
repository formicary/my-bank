package com.abc;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void statementTest() {
        Customer henry = new Customer("Henry");
        Account checking = new Account(AccountTypes.CHECKING);
        Account saving = new Account(AccountTypes.SAVINGS);

        henry.openAccount(checking);
        henry.openAccount(saving);
        checking.deposit(500);
        checking.withdraw(40);
        checking.withdraw(20);
        checking.withdraw(600);

        saving.deposit(1000);
        saving.deposit(1200);
        saving.withdraw(400);

        List<Transaction> checkingTransactions = checking.getTransactions();
        List<Transaction> savingTransactions = saving.getTransactions();

        String expected = "Statement for " + henry.getName() + "\n" +
                "\n" +
                "Checking Account\n";

        double checkTotal = 0;
        for (Transaction t : checkingTransactions) {
            expected += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) +
                    " @ " + t.getTransactionDate() + "\n";
            checkTotal += t.getAmount();
        }
        expected += "Total " + toDollars(checkTotal) + "\n\n";

        expected += "Savings Account\n";
        double savingTotal = 0;
        for (Transaction t : savingTransactions) {
            expected += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) +
                    " @ " + t.getTransactionDate() + "\n";
            savingTotal += t.getAmount();
        }
        expected += "Total " + toDollars(savingTotal) + "\n\n";

        expected += "Total In All Accounts " + toDollars(savingTotal + checkTotal);

        assertEquals(expected, henry.getStatement());
    }

    @Test
    public void testAccountTypes() {
        Customer oscar = new Customer("Oscar");
        List<Account> accounts = oscar.getAccounts();
        oscar.openAccount(new Account(AccountTypes.SAVINGS));
        assertEquals(AccountTypes.SAVINGS, accounts.get(0).getAccountType());

        oscar.openAccount(new Account(AccountTypes.CHECKING));
        assertEquals(AccountTypes.CHECKING, accounts.get(1).getAccountType());

        oscar.openAccount(new Account(AccountTypes.MAXI_SAVINGS));
        assertEquals(AccountTypes.MAXI_SAVINGS, accounts.get(2).getAccountType());
    }

    @Test
    public void transfer() {
        Customer bill = new Customer("Bill");

        Account saving = new Account(AccountTypes.SAVINGS);
        saving.deposit(2000);
        bill.openAccount(saving);

        Account checking = new Account(AccountTypes.CHECKING);
        checking.deposit(5000);
        bill.openAccount(checking);

        bill.transfer(checking, saving, 4000);
        assertEquals(6000, saving.getBalance(), DOUBLE_DELTA);
    }

    private String toDollars(double d){
        return String.format("$%,.2f", d);
    }
}
