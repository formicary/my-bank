package com.abc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

    // Customer can transfer balance between their accounts
    @Test
    public void BalanceTransfer_TransferFunds_BalanceTransfersFromOneAccountToAnother(){
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount1 = new Account(Account.CHECKING);
        Account checkingAccount2 = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(checkingAccount1);
        john.openAccount(checkingAccount2);
        checkingAccount1.depositFunds(10.00);
        checkingAccount2.depositFunds(5.00);

        // Act
        john.transferFunds(checkingAccount1, checkingAccount2, 5.00);

        // Assert
        assertEquals(10.00, checkingAccount2.sumTransactions());
    }

    // Customer should not be able to transfer more than account holds
    @Test (expected = IllegalArgumentException.class)
    public void NegativeBalanceTransfer_TransferFunds_BalanceTransferShouldBeStopped() {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount1 = new Account(Account.CHECKING);
        Account checkingAccount2 = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(checkingAccount1);
        john.openAccount(checkingAccount2);
        checkingAccount1.depositFunds(5.00);
        checkingAccount2.depositFunds(5.00);

        // Act / Assert
        john.transferFunds(checkingAccount1, checkingAccount2, 100.00);
    }


    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(true);
    }
}
