/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import com.abc.Exceptions.CustomerNameAlreadyExistsException;
import com.abc.Exceptions.NotEnoughFundsAvailableException;



public class main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws NotEnoughFundsAvailableException {

        Bank bank = new Bank();
        Customer jake = new Customer("Jake");
        Customer jake_two = new Customer("Jake");

        try{
            bank.addCustomer(jake);
            bank.addCustomer(jake_two);


        } catch (CustomerNameAlreadyExistsException ex) {
            ex.printStackTrace();
        }
        
        System.out.println(bank.getFirstCustomer());
        
        Account checking = new Account(AccountType.CHECKING);
        checking.deposit(4000.0);

        checking.withdraw(30.0);
        checking.withdraw(200.0);
        checking.withdraw(600.0);
        checking.withdraw(365.0);
        checking.withdraw(85.0);
        checking.withdraw(52.0);
        
        System.out.println("Days Since W: "+checking.daysSinceLastWithdrawal());

        

    }

}
