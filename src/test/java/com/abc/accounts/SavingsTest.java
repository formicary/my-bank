package com.abc.accounts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @project MyBank
 */
public class SavingsTest {

    private Savings mockAcc;
    private double expIntRate;
    private double expSecIntRate;
    private double expAccrueRate;
    private double expSecAccrueRate;

    @BeforeEach
    public void init(){
        mockAcc = new Savings(LocalDateTime.now());
        expIntRate = 0.001;
        expSecIntRate = 0.002;
        expAccrueRate = 2.7397260273972604E-6;
        expSecAccrueRate = 5.479452054794521E-6;
    }

    @Test
    public void testInitInterestRate(){
        assertEquals(expIntRate,mockAcc.getInterestRate());
    }

    @Test
    public void testInitAccrueRate(){
        assertEquals(expAccrueRate,mockAcc.getAccrueRate());
    }

    @Test
    public void testInitSecInterestRate(){
        assertEquals(expSecIntRate,mockAcc.getSecInterestRate());
    }

    @Test
    public void testInitSecAccrueRate(){
        assertEquals(expSecAccrueRate,mockAcc.getSecAccrueRate());
    }

    @Test
    public void testCompoundInterestOne(){
        mockAcc.deposit(500);
        mockAcc.compoundInterest();
        double expected = 500.5;
        assertEquals(expected, mockAcc.getBalance());
    }

    @Test
    public void testCompoundInterestTwo(){
        mockAcc.deposit(2000);
        mockAcc.compoundInterest();
        double expected = 2003;
        assertEquals(expected, mockAcc.getBalance());
    }

    @Test
    public void testAccrueInterest(){

        mockAcc.accrueInterest();
        double expected = expIntRate + expAccrueRate;
        assertEquals(expected, mockAcc.getInterestRate());
        expected = expSecIntRate + expSecAccrueRate;
        assertEquals(expected, mockAcc.getSecInterestRate());
    }

    @Test
    public void testTenDaysPassing(){
        mockAcc.deposit(999);
        LocalDateTime tenDaysLater = mockAcc.getDateOfLastUpdate().plusDays(10);
        mockAcc.updateAccount(tenDaysLater);

        double expected = 1009.1966070168221;

        assertEquals(expected, mockAcc.getBalance());
    }

    @Test
    public void testTotalInterest(){
        mockAcc.deposit(999);
        LocalDateTime tenDaysLater = mockAcc.getDateOfLastUpdate().plusDays(10);
        mockAcc.updateAccount(tenDaysLater);

        double expected = 1009.1966070168221 - 999;

        assertEquals(expected, mockAcc.totalInterestEarned());
    }

}
