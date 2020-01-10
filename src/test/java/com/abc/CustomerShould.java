package com.abc;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerShould {
    @Test
    public void GenerateStatement_GivenTheyHaveOpenAccounts() {

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer customer = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
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
                "Total In All Accounts $3,900.00", customer.getStatement());
    }

    @Test
    public void BeAbleToOpenOneAccount() {
        Customer customer = new Customer("Oscar");

        customer.openAccount(new SavingsAccount());

        assertEquals(1, customer.getNumberOfAccounts());
    }

    @Test
    public void BeAbleToOpenTwoAccounts() {
        Customer customer = new Customer("Oscar");

        customer.openAccount(new SavingsAccount());
        customer.openAccount(new CheckingAccount());

        assertEquals(2, customer.getNumberOfAccounts());
    }

    @Test
    public void BeAbleToOpenThreeAccounts() {
        Customer customer = new Customer("Oscar");

        customer.openAccount(new SavingsAccount());
        customer.openAccount(new CheckingAccount());
        customer.openAccount(new MaxiSavingsAccount());

        assertEquals(3, customer.getNumberOfAccounts());
    }
}
