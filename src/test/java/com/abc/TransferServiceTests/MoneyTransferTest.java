package com.abc.TransferServiceTests;

import org.junit.Test;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.TransferServiceUtils.TransferMoney;

import static org.junit.Assert.assertEquals;

public class MoneyTransferTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testMoneyTransfer() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        Customer customer = new Customer("Bill");
        bank.addCustomer(customer.openAccount(checkingAccount));
        customer.openAccount(maxiSavingsAccount);

        checkingAccount.deposit(250);
        maxiSavingsAccount.deposit(1000);

        TransferMoney transferService = new TransferMoney();
        transferService.moveMoneyBetweenAccounts(checkingAccount, maxiSavingsAccount, 100);

        double newBalance = checkingAccount.getAccountBalance();
        double newSavingsBalance = maxiSavingsAccount.getAccountBalance();
        assertEquals(150, newBalance, DOUBLE_DELTA);
        assertEquals(1100, newSavingsBalance, DOUBLE_DELTA);

    }
}
