package com.abc.formatters;

import com.abc.accounts.CheckingAccount;
import com.abc.accounts.SavingsAccount;
import com.abc.domain.Account;
import com.abc.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerStatementTest {

    @Test
    public void customerStatement() {
        Account checkingAccount = new Account(CheckingAccount.INSTANCE);
        Account savingsAccount = new Account(SavingsAccount.INSTANCE);

        final Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        final String statement = new CustomerStatementFormatter(henry).getStatement();

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
                "Total In All Accounts $3,900.00", statement);
    }

}