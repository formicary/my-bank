package com.abc.users;

import com.abc.accounts.Account;
import com.abc.accounts.Checking;
import com.abc.accounts.MaxiSavings;
import com.abc.accounts.Savings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Testing customer")
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
    @DisplayName("When an account is opened, one should be present in accounts")
    public void testOpenAccount(){
        mockC.openAccount(checkingAcc);
        assertEquals(checkingAcc,mockC.getAccounts().get(0));
    }

    @Test
    @DisplayName("when 3 accounts are opened, customer should own 3 accounts")
    public void testOpenMultAccounts(){
        mockC.openAccount(checkingAcc);
        mockC.openAccount(savingsAcc);
        mockC.openAccount(maxiAcc);

        assertEquals(3, mockC.getNumberOfAccounts());
    }

    @Test
    @DisplayName("When a transfer is made between two accounts, one acc should withdraw 200, and the other deposit 200")
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
    @DisplayName("when one of each account earns a day of interest, sum of interest should be 54")
    public void testTotalInterestEarned(){
        mockC.openAccount(checkingAcc);
        mockC.openAccount(savingsAcc);
        mockC.openAccount(maxiAcc);

        assumeTrue(mockC.getAccounts().contains(checkingAcc),
                () -> "Customer's openAccount method is not adding a new account correctly");

        checkingAcc.deposit(1000);
        savingsAcc.deposit(2000);
        maxiAcc.deposit(1000);

        assumeTrue(checkingAcc.getBalance()==1000,
                () -> "Account's deposit method is not updating the balance correctly");

        LocalDateTime oldDate = checkingAcc.getDateOfLastUpdate();
        LocalDateTime newDate = oldDate.plusDays(1);

        checkingAcc.updateAccount(newDate);
        savingsAcc.updateAccount(newDate);
        maxiAcc.updateAccount(newDate);

        assumeTrue(checkingAcc.getDateOfLastUpdate() == newDate,
                () -> "Account's updateAccount is not updating the date correctly");

        assertEquals(54, mockC.totalInterestEarned());
    }

    @Test
    @DisplayName("When a statement is requested and customer own no accounts, statement should be empty")
    public void testGetEmpStatementInDollars(){
        String actual = mockC.getStatementInDollars();
        System.out.println(actual);
    }

    @Test
    @DisplayName("when a statement is requested, statement must output each account and their transactions")
    public void testGetStatementInDollars(){
        mockC.openAccount(checkingAcc);
        mockC.openAccount(savingsAcc);
        mockC.openAccount(maxiAcc);

        assumeTrue(mockC.getAccounts().contains(checkingAcc),
                () -> "Customer's openAccount method is not adding a new account correctly");

        checkingAcc.deposit(1000);
        savingsAcc.deposit(2000);
        maxiAcc.deposit(1000);

        assumeTrue(checkingAcc.getBalance()==1000,
                () -> "Account's deposit method is not updating the balance correctly");

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
