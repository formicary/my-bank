
package com.abc.Exceptions;


public class NotEnoughFundsAvailableException extends Exception {

    public NotEnoughFundsAvailableException() {
        super("Not enough funds in account.");
    }
}
