/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import static java.lang.Math.abs;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author LOTWF
 */
public class helper {

    public static String TransactionDate(Date transactionDate){
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        return dateFormat.format(transactionDate);
    }
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    public static double dailyInterest(int days,double annual){
        return (double)(Math.pow((1+annual),(double)days/365)-(double) 1);
    }
}
