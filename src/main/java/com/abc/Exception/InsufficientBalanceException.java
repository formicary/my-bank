package com.abc.Exception;

/**
 * Exception for when an account has inssufficient balance
 */
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(){
        super();
    };

    public InsufficientBalanceException(String errorMessage){
        super(errorMessage);
    };
}
