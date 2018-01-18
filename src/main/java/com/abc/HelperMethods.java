/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import static java.lang.Math.abs;

/**
 *
 * @author yairh
 */
public final class HelperMethods {
    private HelperMethods(){}
    
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    
}
