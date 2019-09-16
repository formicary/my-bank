package com.abc.customer;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.SavingsAccount;
import com.abc.exception.InsufficientBalanceException;
import com.abc.Transaction;
import org.junit.Test;

import com.abc.Money;;

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
            checkingAccount.processTransaction(new Transaction(new Money("100")));
            savingsAccount.processTransaction(new Transaction(new Money("4000")));
            savingsAccount.processTransaction(new Transaction(new Money("-200")));
        } catch(InsufficientBalanceException e) {
            fail("Insufficient Balance exception thrown unexpectedly");
        }

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal -$200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All account $3,900.00", new Statement(henry).getStatement());
    }
}