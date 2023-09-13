package com.abc;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    /**
     * Tests customer statement
     */
    @Test
    public void test_customerStatement(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry" + System.lineSeparator() + System.lineSeparator() +
                "Checking Account" + System.lineSeparator() +
                "  deposit $100.00" + System.lineSeparator() +
                "Total $100.00" + System.lineSeparator() + System.lineSeparator() +
                "Savings Account" + System.lineSeparator() +
                "  deposit $4,000.00" + System.lineSeparator() +
                "  withdrawal $200.00" + System.lineSeparator() +
                "Total $3,800.00" + System.lineSeparator() + System.lineSeparator() +
                "Total In All Accounts $3,900.00", henry.printStatement());
    }

    @Test
    public void test_oneAccountInstance(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount());
        assertEquals(1, oscar.getAccounts().size());
        assertTrue(oscar.getAccounts().stream().anyMatch(account -> account instanceof SavingsAccount));
    }

    @Test
    public void test_moreAccountsInstances() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getAccounts().size());
        assertTrue(oscar.getAccounts().stream().anyMatch(account -> account instanceof SavingsAccount));
        assertTrue(oscar.getAccounts().stream().anyMatch(account -> account instanceof CheckingAccount));
        assertTrue(oscar.getAccounts().stream().anyMatch(account -> account instanceof MaxiSavingsAccount));
    }

    /**
     * Test transfer from one account to another
     * Customer has 3 accounts, one from which the amount is withdrawn,
     * one to which the amount is deposited
     * and one account to check that it was not modified by transfer
     */
    @Test
    public void test_transfer() {
        double fromInitialDeposit = 1000;
        double toInitialDeposit = 1500;
        double neutralInitialDeposit = 50;
        double transferAmount = 500;

        Account from = new CheckingAccount();
        from.deposit(fromInitialDeposit);

        Account to = new CheckingAccount();
        to.deposit(toInitialDeposit);

        Account neutralAccount = new CheckingAccount();
        neutralAccount.deposit(neutralInitialDeposit);

        Customer bruce = new Customer("Bruce");
        bruce.openAccount(from);
        bruce.openAccount(neutralAccount);
        bruce.openAccount(to);
        bruce.transfer(from,to,transferAmount);

        assertEquals(fromInitialDeposit - transferAmount, from.calculateAccountBalance(), 0);
        assertEquals(toInitialDeposit + transferAmount, to.calculateAccountBalance(), 0);
        assertEquals(neutralInitialDeposit, neutralAccount.calculateAccountBalance(), 0);
    }
}
