package com.abc.exception;

/**
 * exception for when an account has inssufficient balance
 */
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(){
        super();
    };

    public InsufficientBalanceException(String errorMessage){
        super(errorMessage);
    };
}
