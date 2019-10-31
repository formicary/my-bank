package com.abc;

import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void testCustomerName() {
        Customer Andy = new Customer("Andy");
        assertEquals("Andy", Andy.getName());
    }

    @Test
    public void testCustomerCanOpenAnAccount() {
        Customer customer = new Customer("Customer");
        customer.openNewAccount(AccountType.CHECKING,"Test account");
        assertEquals(1,customer.getNumberOfAccounts());

    }
    @Test(expected = IllegalArgumentException.class)
    public void testCustomerCannotOpenTwoAccountsWithTheSameName() {
        Customer Ryan = new Customer("Ryan");
        Ryan.openNewAccount(AccountType.CHECKING,"Ryan's checking account");
        Ryan.openNewAccount(AccountType.MAXI_SAVINGS,"Ryan's checking account");
    }

    @Test
    public void testCustomerCanAddMoneyToExistingAccount() {
        Customer Abe = new Customer("Abe");
        Abe.openNewAccount(AccountType.MAXI_SAVINGS, "Abe's Maxi account");
        Abe.depositIntoAccount(100, "Abe's Maxi account");
        assertEquals(100,Abe.getAccount("Abe's Maxi account").get(0).getAmount(),1e-9);

    }
    @Test(expected = IllegalArgumentException.class)
    public void testCustomerCannotDepositIntoAnotherCustomersAccount() {
        Customer Alan = new Customer("Alan");
        Customer Paul = new Customer("Paul");
        Alan.openNewAccount(AccountType.MAXI_SAVINGS, "Alan's Maxi account");
        Alan.depositIntoAccount(100, "Alan's Maxi account");
        Paul.depositIntoAccount(40, "Alan's Maxi account");

    }
    @Test(expected = IllegalArgumentException.class)
    public void testCustomerCannotWithdrawFromAnotherCustomersAccount() {
        Customer Mike = new Customer("Mike");
        Customer thief = new Customer("thief");
        Mike.openNewAccount(AccountType.MAXI_SAVINGS, "Mike's Maxi account");
        Mike.depositIntoAccount(100, "Mike's Maxi account");
        thief.withDrawFromAccount(100, "Mike's Maxi account");

    }

    @Test
    public void testTotalInterestEarnedFromMultipleAccountsWithBaseInterest() {
        Customer Angela = new Customer("Angela");

        Angela.openNewAccount(AccountType.MAXI_SAVINGS,"Angela's Maxi account");
        Angela.depositIntoAccount(10, "Angela's Maxi account");
        Angela.depositIntoAccount(10, "Angela's Maxi account", Instant.now().plus(15, ChronoUnit.DAYS));

        Angela.openNewAccount(AccountType.SAVINGS,"Angela's Savings account");
        Angela.depositIntoAccount(10, "Angela's Savings account");
        Angela.depositIntoAccount(10, "Angela's Savings account", Instant.now().plus(10, ChronoUnit.DAYS));

        Angela.openNewAccount(AccountType.CHECKING,"Angela's Checking account");
        Angela.depositIntoAccount(10, "Angela's Checking account");
        Angela.depositIntoAccount(10, "Angela's Checking account", Instant.now().plus(10, ChronoUnit.DAYS));

        assertEquals(0.02056766041+0.00027397598+0.00027397598,Angela.totalInterestEarned(),1e-9);
    }
    @Test
    public void testTotalInterestEarnedFromMultipleAccountsWithSpecialInterest() {
        Customer Bradley = new Customer("Bradley");

        Bradley.openNewAccount(AccountType.MAXI_SAVINGS,"Bradley's Maxi account");
        Bradley.depositIntoAccount(10, "Bradley's Maxi account");
        Bradley.depositIntoAccount(10, "Bradley's Maxi account", Instant.now().plus(5, ChronoUnit.DAYS));

        Bradley.openNewAccount(AccountType.SAVINGS,"Bradley's Savings account");
        Bradley.depositIntoAccount(10000, "Bradley's Savings account");
        Bradley.depositIntoAccount(10, "Bradley's Savings account", Instant.now().plus(10, ChronoUnit.DAYS));

        Bradley.openNewAccount(AccountType.CHECKING,"Bradley's Checking account");
        Bradley.depositIntoAccount(10, "Bradley's Checking account");
        Bradley.depositIntoAccount(10, "Bradley's Checking account", Instant.now().plus(10, ChronoUnit.DAYS));

        assertEquals(0.0001369870517+0.4931628445+0.00027397598,Bradley.totalInterestEarned(),1e-9);
    }
    @Test
    public void testTotalInterestEarnedFromMultipleAccountsWithEdgeCaseInterest() {
        Customer Charles = new Customer("Charles");

        Instant now = Instant.now();
        Instant tenDaysTime = now.plus(10,ChronoUnit.DAYS);

        Charles.openNewAccount(AccountType.MAXI_SAVINGS,"Charles's Maxi account");
        Charles.depositIntoAccount(10, "Charles's Maxi account",now);
        Charles.depositIntoAccount(10, "Charles's Maxi account", tenDaysTime);

        Charles.openNewAccount(AccountType.SAVINGS,"Charles's Savings account");
        Charles.depositIntoAccount(999.99, "Charles's Savings account");
        Charles.depositIntoAccount(10, "Charles's Savings account", Instant.now().plus(10, ChronoUnit.DAYS));

        Charles.openNewAccount(AccountType.CHECKING,"Charles's Checking account");
        Charles.depositIntoAccount(10, "Charles's Checking account");
        Charles.depositIntoAccount(10, "Charles's Checking account", Instant.now().plus(10, ChronoUnit.DAYS));

        assertEquals(0.000273975980507+0.01095887105+0.00027397598,Charles.totalInterestEarned(),1e-9);
    }

    @Test
    public void testAccountStatementsHaveCorrectContents() {
        Customer Bob = new Customer("Bob");
        Bob.openNewAccount(AccountType.CHECKING, "Bob's Checking account");
        Bob.openNewAccount(AccountType.SAVINGS, "Bob's Savings account");
        Bob.openNewAccount(AccountType.MAXI_SAVINGS, "Bob's Maxi_saving account");
        Instant now = Instant.now();
        Instant nowPlus50 = now.plus(50,ChronoUnit.DAYS);
        String nowString = Transaction.formatter.format(now);
        String nowPlus50String = Transaction.formatter.format(nowPlus50);
        Bob.depositIntoAccount(10,"Bob's Checking account",now);
        Bob.depositIntoAccount(20,"Bob's Checking account",now);
        Bob.withDrawFromAccount(20,"Bob's Checking account",now);
        Bob.depositIntoAccount(400,"Bob's Savings account",now);
        Bob.withDrawFromAccount(400,"Bob's Savings account", nowPlus50);

        String expectedOutput = "Statement for Bob\n\nChecking Account: Bob's Checking account"
                                +"\n\t$10.00 deposit on "+nowString+"\n\t$20.00 deposit on "+nowString
                                +"\n\t$20.00 withdrawal on "+nowString+"\n"+"Account Balance $10.00\nInterest Earned $0.00"
                                +"\n\nSavings Account: Bob's Savings account\n\t$400.00 deposit on "+nowString+"\n\t"
                                +"$400.00 withdrawal on "+nowPlus50String+"\nAccount Balance $0.05\nInterest Earned $0.05"
                                +"\n\nMaxi-Savings Account: Bob's Maxi_saving account\n"+"Account Balance $0.00\nInterest Earned $0.00"
                                +"\n\nTotal In All Accounts $10.05\nTotal Interest Earned $0.05";

        assertEquals(expectedOutput,Bob.getStatement());

    }

    @Test
    public void testTransferBetweenAccountsDepositsCorrectly() {
        Customer Frank = new Customer("Frank");
        Frank.openNewAccount(AccountType.CHECKING,"Frank's Checking account");
        Frank.depositIntoAccount(400,"Frank's Checking account");
        Frank.openNewAccount(AccountType.SAVINGS,"Frank's Savings account");
        Frank.transferBetweenAccounts("Frank's Checking account", "Frank's Savings account", 400);
        assertEquals(400,Frank.getAccount("Frank's Savings account").get(0).getAmount(),1e-9);
    }
    @Test
    public void testTransferBetweenAccountsWithdrawsCorrectly() {
        Customer James = new Customer("James");
        James.openNewAccount(AccountType.CHECKING,"James's Checking account");
        James.depositIntoAccount(400,"James's Checking account");
        James.openNewAccount(AccountType.SAVINGS,"James's Savings account");
        James.transferBetweenAccounts("James's Checking account", "James's Savings account", 400);
        assertEquals(-400,James.getAccount("James's Checking account").get(1).getAmount(),1e-9);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentCustomersCannotTransferBetweenTheir() {
        Customer Ed = new Customer("Ed");
        Ed.openNewAccount(AccountType.CHECKING,"Ed's Checking account");
        Ed.depositIntoAccount(400,"Ed's Checking account");

        Customer Jan = new Customer("Jan");
        Jan.openNewAccount(AccountType.CHECKING,"Jan's Checking account");
        Jan.depositIntoAccount(400,"Jan's Checking account");

        Jan.transferBetweenAccounts("Jan's Checking account","Ed's Checking account",400);

    }


}
