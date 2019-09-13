package com.abc;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;
import com.abc.Account.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatementTest {

    @Test // Test customer statement generation
    public void getStatement() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry");
        henry.addAccount(checkingAccount);
        henry.addAccount(savingsAccount);
        try{
            checkingAccount.processTransaction(new Transaction(100.0));
            savingsAccount.processTransaction(new Transaction(4000));
            savingsAccount.processTransaction(new Transaction(-200));
        } catch(Exception e) {
            fail("Exception thrown unexpectedly");
        }

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
                "Total In All Account $3,900.00", new Statement(henry).getStatement());
    }
}