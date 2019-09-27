package com.abc.accounts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @project MyBank
 */
public class MaxiSavingsTest {

    private MaxiSavings mockAcc;
    private double expIntRate;
    private double expSecIntRate;
    private double expAccrueRate;
    private double expSecAccrueRate;

    @BeforeEach
    public void init(){
        mockAcc = new MaxiSavings(LocalDateTime.now());
        expIntRate = 0.05;
        expAccrueRate = 1.3698630136986303E-4;
        expSecIntRate = 0.001;
        expSecAccrueRate = 2.7397260273972604E-6;
    }

    @Test
    public void testInitInterestRate(){
        assertEquals(expIntRate,mockAcc.getIntRate());
    }

    @Test
    public void testInitAccrueRate(){
        assertEquals(expAccrueRate,mockAcc.getAccrueRate());
    }

    @Test
    public void testInitSecInterestRate(){
        assertEquals(expSecIntRate,mockAcc.getSecIntRate());
    }

    @Test
    public void testInitSecAccrueRate(){
        assertEquals(expSecAccrueRate,mockAcc.getSecAccrueRate());
    }

    @Test
    public void testAccrueInterest(){
        mockAcc.accrueInterest();

        assertEquals(expIntRate + expAccrueRate, mockAcc.getIntRate());
        assertEquals(expSecIntRate + expSecAccrueRate, mockAcc.getSecIntRate());
    }

    @Test
    public void testWithdrawalInPastTenDaysTrue(){

        mockAcc.withdraw(300, mockAcc.getDateOfLastUpdate().plusDays(2));
        mockAcc.deposit(1000, mockAcc.getDateOfLastUpdate().plusDays(4));
        mockAcc.deposit(500, mockAcc.getDateOfLastUpdate().plusDays(6));

        assertTrue(mockAcc.hadWithdrawalInPast(10));
    }

    @Test
    public void testWithdrawalInPastTenDaysFalse(){

        mockAcc.withdraw(300, mockAcc.getDateOfLastUpdate().plusDays(2));
        mockAcc.deposit(1000, mockAcc.getDateOfLastUpdate().plusDays(4));
        mockAcc.deposit(500, mockAcc.getDateOfLastUpdate().plusDays(7));

        assertFalse(mockAcc.hadWithdrawalInPast(10));
    }



}
