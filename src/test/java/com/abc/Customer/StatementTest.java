package com.abc.Customer;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;
import com.abc.Account.SavingsAccount;
import com.abc.Customer.Customer;
import com.abc.Customer.Statement;
import com.abc.Exception.InsufficientBalanceException;
import com.abc.Transaction;
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
        } catch(InsufficientBalanceException e) {
            fail("Insufficient Balance Exception thrown unexpectedly");
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