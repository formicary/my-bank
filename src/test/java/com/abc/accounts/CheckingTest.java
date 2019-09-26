package com.abc.accounts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @project MyBank
 */
public class CheckingTest {

    private Account mockAcc;
    private double expInterestRate;
    private double expAccrueRate;

    @BeforeEach
    public void init(){
        mockAcc = new Checking(LocalDateTime.now());
        expInterestRate = 0.001;
        expAccrueRate = 2.7397260273972604E-6;
    }

    @Test
    public void testInitInterestRate(){
        assertEquals(expInterestRate,mockAcc.getInterestRate());
    }

    @Test
    public void testInitAccrueRate(){
        assertEquals(expAccrueRate,mockAcc.getAccrueRate());
    }

    @Test
    public void testAccrueAndCompBalance(){
        mockAcc.deposit(1000);
        mockAcc.acrrueAndCompBalance(10);
        double expected = 1010.1695285021814;
        assertEquals(expected, mockAcc.getBalance());
    }

    @Test
    public void testTotalInterest(){
        mockAcc.deposit(1000);
        LocalDateTime tenDaysLater = mockAcc.getDateOfLastUpdate().plusDays(10);
        mockAcc.updateAccount(tenDaysLater);

        double expected = 1010.1695285021814 - 1000.0;

        assertEquals(expected, mockAcc.totalInterestEarned());
    }

    @Test
    public void testToString(){
        assertEquals("Checking Account", mockAcc.toString());
    }

}