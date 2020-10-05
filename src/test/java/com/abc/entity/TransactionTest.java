package com.abc.entity;

import com.abc.entity.impl.TransactionImpl;
import com.abc.exception.InvalidAmountException;
import org.junit.Test;

public class TransactionTest {

    @Test(expected = InvalidAmountException.class)
    public void transactionAmountCannotBeNull(){
        Transaction t1 = new TransactionImpl(null);
    }
}
