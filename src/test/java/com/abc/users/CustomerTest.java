package com.abc.users;

import com.abc.accounts.Account;
import com.abc.accounts.Checking;
import com.abc.accounts.MaxiSavings;
import com.abc.accounts.Savings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    private Customer mockC;
    private Account checkingAcc;
    private Account savingsAcc;
    private Account maxiAcc;

    @BeforeEach
    public void init(){
        mockC = new Customer("Bob");
        checkingAcc = new Checking(LocalDateTime.now());
        savingsAcc = new Savings(LocalDateTime.now());
        maxiAcc = new MaxiSavings(LocalDateTime.now());
    }

    @Test
    public void testOpenAccount(){
        mockC.openAccount(checkingAcc);
        assertEquals(checkingAcc,mockC.getAccounts().get(0));
    }

    @Test
    public void testNumbOfAccounts(){
        mockC.openAccount(checkingAcc);
        mockC.openAccount(savingsAcc);
        mockC.openAccount(maxiAcc);

        assertEquals(3, mockC.getNumberOfAccounts());
    }

    @Test
    public void testTransfer(){
        mockC.openAccount(checkingAcc);
        mockC.openAccount(savingsAcc);

        checkingAcc.deposit(1000);
        savingsAcc.deposit(1000);

        mockC.transfer(checkingAcc,savingsAcc,200);

        Account checkingAcc = mockC.getAccounts().get(0);
        Account savingsAcc = mockC.getAccounts().get(1);

        assertEquals(800, checkingAcc.getBalance());
        assertEquals(1200, savingsAcc.getBalance());
    }

    @Test
    public void testTotalInterestEarned(){
        mockC.openAccount(checkingAcc);
        mockC.openAccount(savingsAcc);
        mockC.openAccount(maxiAcc);

        checkingAcc.deposit(1000);
        savingsAcc.deposit(2000);
        maxiAcc.deposit(1000);

        checkingAcc.updateAccount(checkingAcc.getDateOfLastUpdate().plusDays(1));
        savingsAcc.updateAccount(savingsAcc.getDateOfLastUpdate().plusDays(1));
        maxiAcc.updateAccount(maxiAcc.getDateOfLastUpdate().plusDays(1));

        double expected = 54;

        assertEquals(expected, mockC.totalInterestEarned());

    }

    @Test
    public void testGetStatementInDollars(){
        mockC.openAccount(checkingAcc);
        mockC.openAccount(savingsAcc);
        mockC.openAccount(maxiAcc);

        checkingAcc.deposit(1000);
        savingsAcc.deposit(2000);
        maxiAcc.deposit(1000);

        checkingAcc.withdraw(500, checkingAcc.getDateOfLastUpdate().plusDays(1));
        maxiAcc.withdraw(30, maxiAcc.getDateOfLastUpdate().plusDays(1));
        savingsAcc.updateAccount(savingsAcc.getDateOfLastUpdate().plusDays(1));

        String expected = "Statement for Bob\n" +
                "\n" +
                "Checking Account\n" +
                "  Deposit $1,000.00\n" +
                "  Withdrawal $500.00\n" +
                "Total $501.00\n" +
                "\n" +
                "Savings Account\n" +
                "  Deposit $2,000.00\n" +
                "Total $2,003.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  Deposit $1,000.00\n" +
                "  Withdrawal $30.00\n" +
                "Total $1,020.00\n" +
                "\n" +
                "Total In All Accounts $3,524.00";

        assertEquals(expected, mockC.getStatementInDollars());
    }
}
