package com.abc.transaction;

import com.abc.util.ZeroAmountException;

/**
 * Created by sameen on 26/08/2018.
 */
public class CreditTransaction extends Transaction {

    public CreditTransaction(TransactionType type, double amount) throws ZeroAmountException {
        super(type, amount);
    }
}
