package com.abc.accounts;

import com.abc.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @project MyBank
 */
public class AccountTest {

    private static Account mockAcc;

    @BeforeEach
    public void init(){

        LocalDateTime date = LocalDateTime.now();
        mockAcc = new Account(date) {
            @Override
            protected void compoundInterest() {

            }

            @Override
            protected void accrueInterest() {

            }
        };
    }

    @Test
    public void testDepositSuccess(){
        double expected = 2000;
        mockAcc.deposit(expected);
        double actual = mockAcc.getBalance();
        assertEquals(expected,actual);
    }

    @Test
    public void testDepositThrows(){
        assertThrows(IllegalArgumentException.class, () -> mockAcc.deposit(-500));
    }

    @Test
    public void testWithdrawSuccess(){
        double expected = -500.50;
        mockAcc.withdraw(-expected);
        double actual = mockAcc.getBalance();
        assertEquals(expected,actual);
    }

    @Test
    public void testWithdrawThrows(){
        assertThrows(IllegalArgumentException.class, () -> mockAcc.withdraw(-500));
    }

    @Test
    public void testAddTransaction(){
        Transaction expected = new Transaction(500);
        mockAcc.addTransaction(expected);
        assertTrue(mockAcc.getTransactions().contains(expected));
    }

    @Test
    public void testSumTransitions(){
        mockAcc.addTransaction(new Transaction(1000));
        mockAcc.addTransaction(new Transaction(-500));
        mockAcc.addTransaction(new Transaction(5.56));

        double expected = 505.56;

        assertEquals(expected, mockAcc.sumTransactions());
    }

    @Test
    public void testUpdateDateOfLastUpdate(){
        LocalDateTime expected = mockAcc.getDateOfLastUpdate().plusMonths(5);
        mockAcc.updateDateOfLastUpdate(expected);
        assertEquals(expected,mockAcc.getDateOfLastUpdate());
    }

    @Test
    public void testInterestRate(){
        double expected = 0.5;
        mockAcc.setInterestRate(0.5);
        assertEquals(expected,mockAcc.getInterestRate());
    }

    @Test
    public void testAccrueRate(){
        double expected = 0.5;
        mockAcc.setAccrueRate(expected);
        assertEquals(expected,mockAcc.getAccrueRate());
    }

    @Test
    public void testUpdateAccountWithParam(){
        LocalDateTime date = LocalDateTime.now().plusDays(10);
        mockAcc.updateAccount(date);
        assertEquals(date, mockAcc.getDateOfLastUpdate());
    }

    @Test
    public void testTotalInterestEarned(){
        mockAcc.deposit(1000);
        mockAcc.deposit(500);
        mockAcc.withdraw(200);

        double expected = 0;
        double actual = mockAcc.totalInterestEarned();

        assertEquals(expected,actual);
    }

    @Test
    public void testStatementInDollars(){
        mockAcc.deposit(1000);
        mockAcc.deposit(500);
        mockAcc.withdraw(200);

        String expected = "Account\n" +
                "  Deposit $1,000.00\n" +
                "  Deposit $500.00\n" +
                "  Withdrawal $200.00\n" +
                "Total $1,300.00";
        assertEquals(expected, mockAcc.getStatementInDollars());
    }

}
