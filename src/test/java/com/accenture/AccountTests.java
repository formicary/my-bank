package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.accounts.AccountFactory;
import org.junit.Test;


import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class AccountTests {

    @Test
    public void accountCanCorrectlyPrintItsStatement() {
        // Create account and apply some transactions.
        Account account = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, "testId");

        Transaction depositTransaction = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(1000))
                .build();

        Transaction interestTransaction = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.INTEREST)
                .setAmount(DollarAmount.fromInt(25))
                .build();

        account.applyTransaction(depositTransaction);
        account.applyTransaction(interestTransaction);

        String expectedStatement = "Account Statement. \n" +
                "Transaction Type: DEPOSIT  Amount:1000.00 USD\n" +
                "Transaction Type: INTEREST  Amount:25.00 USD\n" +
                "Current Balance: 1025.00 USD";

        String actualStatement = account.generateStatement(false);

        assertEquals(expectedStatement, actualStatement);
    }

    @Test
    public void accountCanCorrectlyPrintItsStatementWithTotalInterestPaid() {
        Account account = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, "testId");

        Transaction depositTransaction = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(1000))
                .build();

        Transaction interestTransaction = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.INTEREST)
                .setAmount(DollarAmount.fromInt(25))
                .build();

        account.applyTransaction(depositTransaction);
        account.applyTransaction(interestTransaction);


        String expectedStatement = "Account Statement. \n" +
                "Transaction Type: DEPOSIT  Amount:1000.00 USD\n" +
                "Transaction Type: INTEREST  Amount:25.00 USD\n" +
                "Total Interest Paid: 25.00 USD\n" +
                "Current Balance: 1025.00 USD";

        String actualStatement = account.generateStatement(true);

        assertEquals(expectedStatement, actualStatement);
    }

    @Test
    public void accountCanApplyTransaction() {
        Account account = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, "");
        Transaction testTransaction = new Transaction.Builder().setType(Transaction.Type.INTEREST).setAmount(DollarAmount.fromInt(20)).setDescription("Test").build();

        assertEquals(DollarAmount.fromInt(0), account.getAccountBalance());
        account.applyTransaction(testTransaction);

        assertEquals(DollarAmount.fromInt(20), account.getAccountBalance());
    }


    @Test
    public void accountCanCorrectlyGetBalance() {
        Account account = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, "");
        assertEquals(DollarAmount.fromInt(0), account.getAccountBalance());

        Transaction testTransaction1 = new Transaction.Builder().setType(Transaction.Type.DEPOSIT).setAmount(DollarAmount.fromInt(100)).setDescription("Test").build();
        Transaction testTransaction2 = new Transaction.Builder().setType(Transaction.Type.WITHDRAWAL).setAmount(DollarAmount.fromInt(20)).setDescription("Test").build();
        Transaction testTransaction3 = new Transaction.Builder().setType(Transaction.Type.INTEREST).setAmount(DollarAmount.fromInt(50)).setDescription("Test").build();
        Transaction testTransaction4 = new Transaction.Builder().setType(Transaction.Type.DEPOSIT).setAmount(DollarAmount.fromInt(50)).setDescription("Test").build();
        account.applyTransaction(testTransaction1);
        account.applyTransaction(testTransaction2);
        account.applyTransaction(testTransaction3);
        account.applyTransaction(testTransaction4);

        assertEquals(DollarAmount.fromInt(180), account.getAccountBalance());
    }


    @Test
    public void accountCanCorrectlyGetTotalInterestPaid() {
        Account account = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, "");
        assertEquals(DollarAmount.fromInt(0), account.getTotalInterestPaid());

        Transaction testTransaction1 = new Transaction.Builder().setType(Transaction.Type.DEPOSIT).setAmount(DollarAmount.fromInt(100)).setDescription("Test").build();
        Transaction testTransaction2 = new Transaction.Builder().setType(Transaction.Type.WITHDRAWAL).setAmount(DollarAmount.fromInt(20)).setDescription("Test").build();
        Transaction testTransaction3 = new Transaction.Builder().setType(Transaction.Type.INTEREST).setAmount(DollarAmount.fromInt(60)).setDescription("Test").build();
        Transaction testTransaction4 = new Transaction.Builder().setType(Transaction.Type.DEPOSIT).setAmount(DollarAmount.fromInt(50)).setDescription("Test").build();
        Transaction testTransaction5 = new Transaction.Builder().setType(Transaction.Type.INTEREST).setAmount(DollarAmount.fromInt(33)).setDescription("Test").build();

        account.applyTransaction(testTransaction1);
        account.applyTransaction(testTransaction2);
        account.applyTransaction(testTransaction3);
        account.applyTransaction(testTransaction4);
        account.applyTransaction(testTransaction5);

        assertEquals(DollarAmount.fromInt(93), account.getTotalInterestPaid());
    }


}
