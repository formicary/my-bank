package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.accounts.AccountFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TransferBetweenAccountsTests {

    @Test
    public void customerCanPerformTransferBetweenAccounts() {

        // Create two accounts for test with the same owner
        String ownerId = "ownerId";
        Account accountFrom = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, ownerId);
        Account accountTo = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, ownerId);

        // Put some money in the from account
        Transaction transaction = new Transaction.Builder().setDescription("Test").setAmount(DollarAmount.fromInt(100)).setType(Transaction.Type.DEPOSIT).build();
        accountFrom.applyTransaction(transaction);
        assertEquals(accountFrom.getAccountBalance(), DollarAmount.fromInt(100));

        // Attempt transfer
        Account.transfer(accountFrom, accountTo, DollarAmount.fromInt(30));

        // Assert transfer was successful
        assertEquals(DollarAmount.fromInt(70), accountFrom.getAccountBalance());
        assertEquals(DollarAmount.fromInt(30), accountTo.getAccountBalance());
    }


    @Test(expected = IllegalArgumentException.class)
    public void transferBetweenTwoAccountsMustBelongToSameOwner() {

        // Create two accounts for test with DIFFERENT owners
        Account accountFrom = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, "owner1");
        Account accountTo = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, "owner2");

        // Put some money in the from account
        Transaction transaction = new Transaction.Builder().setDescription("Test").setAmount(DollarAmount.fromInt(100)).setType(Transaction.Type.DEPOSIT).build();
        accountFrom.applyTransaction(transaction);
        assertEquals(accountFrom.getAccountBalance(), DollarAmount.fromInt(100));

        // Attempt transfer
        Account.transfer(accountFrom, accountTo, DollarAmount.fromInt(30));

        // Assert transfer was successful
        assertEquals(DollarAmount.fromInt(70), accountFrom.getAccountBalance());
        assertEquals(DollarAmount.fromInt(30), accountTo.getAccountBalance());
    }

}
