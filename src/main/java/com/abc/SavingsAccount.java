/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

/**
 *
 * @author prarthana
 */
public class SavingsAccount extends Account {

    public SavingsAccount(Customer owner, int accountType) {
        super(owner, accountType);
    }

    @Override
    public double interestEarned(double amount) {

        if (amount <= 1000) {
            return amount * 0.001 / DAYS_IN_A_YEAR;
        } else {
            return 1000.0 * 0.001 / DAYS_IN_A_YEAR + (amount - 1000.0) * 0.002 / DAYS_IN_A_YEAR;
        }
    }
}
