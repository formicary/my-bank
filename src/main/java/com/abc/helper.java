/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import static java.lang.Math.abs;


/**
 *
 * @author LOTWF
 */
public class helper {
   
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public static double dailyInterest(int days,double annual){
        return (double)(Math.pow((1+annual),(double)days/365)-(double) 1);
    }
}
