package com.abc.accounts;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

/**
 * @project MyBank
 */
public class SavingsTest {

    private Account mockAcc;
    private double expInterestRate;
    private double expSecIntRate;
    private double expAccrueRate;
    private double expSecAccrueRate;

    @BeforeEach
    public void init(){
        mockAcc = new Checking(LocalDateTime.now());
        expInterestRate = 0.001;
        expSecIntRate = 0.002;
        expAccrueRate = 2.7397260273972604E-6;
        expSecAccrueRate = 5.479452054794521E-6;
    }



}
