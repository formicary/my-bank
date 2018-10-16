package com.abc;

public class NotFoundException extends Exception{

    private Account account;
    private Customer customer;
    private Transaction transaction;

    public NotFoundException(Account a){
        super("Could not find account.");
        this.account = a;
        this.customer = null;
        this.transaction = null;

    }

    public NotFoundException(Customer c){
        super("Could not find customer.");
        this.customer = c;
        this.account = null;
        this.transaction = null;

    }

    public NotFoundException(Transaction t){
        super("Could not find transaction.");
        this.transaction = t;
        this.customer = null;
        this.account = null;
    }
}