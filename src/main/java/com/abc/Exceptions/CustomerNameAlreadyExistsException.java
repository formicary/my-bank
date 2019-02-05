
package com.abc.Exceptions;


public class CustomerNameAlreadyExistsException extends Exception {

    public CustomerNameAlreadyExistsException() {
        super("Customer with this name already exists in bank.");
    }
}
