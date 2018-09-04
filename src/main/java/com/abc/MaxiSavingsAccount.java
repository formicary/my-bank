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
public class MaxiSavingsAccount extends Account {
    
    public MaxiSavingsAccount(Customer owner, int accountType) {
        super(owner, accountType);
    }
    
     @Override
    public double interestEarned(double amount) {
        return 0.0;
    }
    
    
    
    

}