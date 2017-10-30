/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

/**
 *
 * @author James Rogers
 */
public class IllegalAccountException extends Exception {
    public IllegalAccountException(String message) {
        super(message);
    }
}