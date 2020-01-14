package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.Account.Account;
import com.abc.Account.AccountType;
import com.abc.Account.CreateAccount;
import com.abc.Bank.Customer;

public class CustomerTest {

    @Test //Test customer statement generation
    public void customer_statement_test() {

        Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);
        Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);

        Customer carl = new Customer("Carl").openAccount(checkingAcc).openAccount(savingsAcc);

        checkingAcc.deposit(100.0);
        savingsAcc.deposit(4000.0);
        savingsAcc.withdraw(200.0);
        System.out.println(carl.getStatement());

        assertEquals("Statement for Carl\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", carl.getStatement());
        
    }

    @Test
    public void one_account_test() {
        Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
        Customer ehsun = new Customer("Ehsun").openAccount(savingsAcc);
        assertEquals(1, ehsun.getNumberOfAccounts());
    }

    @Test
    public void two_accounts_test() {
        Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
        Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);
        Customer ehsun = new Customer("Ehsun")
                .openAccount(savingsAcc)
                .openAccount(checkingAcc);
        assertEquals(2, ehsun.getNumberOfAccounts());
    }

    @Test
    public void three_accounts_test() {
        Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
        Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);
        Account maxiSavingsAcc = CreateAccount.createAccount(AccountType.MAXI_SAVINGS);

        Customer ehsun = new Customer("Ehsun")
                .openAccount(savingsAcc)
                .openAccount(checkingAcc)
                .openAccount(maxiSavingsAcc);
        assertEquals(3, ehsun.getNumberOfAccounts());
    }

    @Test
    public void transfer_test() {
        Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
        Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);

        Customer ehsun = new Customer("Ehsun")
                .openAccount(savingsAcc)
                .openAccount(checkingAcc);

        savingsAcc.deposit(135.0);
        ehsun.transfer(savingsAcc, checkingAcc, 35);

        assertEquals(100.0, savingsAcc.getBalance(), -Double.MAX_VALUE);

    }

    @Test(expected = IllegalArgumentException.class)
    public void illegal_source_transfer_test() {
        Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
        Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);

        Customer ehsun = new Customer("Ehsun")
                .openAccount(checkingAcc);

        savingsAcc.deposit(3000.0);
        ehsun.transfer(savingsAcc, checkingAcc, 150);


    }

    @Test(expected = IllegalArgumentException.class)
    public void illegal_destination_transfer_test() {
        Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
        Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);

        Customer ehsun = new Customer("Ehsun")
                .openAccount(checkingAcc);

        savingsAcc.deposit(2000.0);
        ehsun.transfer(checkingAcc, savingsAcc, 300);


    }
}
