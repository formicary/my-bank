/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

/**
 *return amount * 0.001;
 * @author David
 */
public class CheckingAccount extends Account
{
    public static final double INTEREST_RATE = 0.001;
    
    public CheckingAccount()
    {
        super(Account.CHECKING);
    }
    
    @Override
    public double interestEarned() 
    {
        double amount = sumTransactions();
        if (amount <= 0) 
        {
            return 0;
        } else 
        {
            return amount * INTEREST_RATE;
        }
    }
}
