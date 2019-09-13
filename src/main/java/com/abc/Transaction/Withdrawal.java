package com.abc.Transaction;

import com.abc.DateProvider;

public class Withdrawal extends Transaction {

    public Withdrawal(double amount){
        super(-amount);
    }
}
