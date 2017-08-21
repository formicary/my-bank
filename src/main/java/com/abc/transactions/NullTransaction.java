package com.abc.transactions;

import com.abc.accounts.Account;
import com.abc.utilities.Money;

/**
 * Created by vahizan on 18/08/2017.
 */
public class NullTransaction extends AbstractTransaction{
    private static NullTransaction nullTransaction = new NullTransaction();
    private NullTransaction(){}
    public static NullTransaction getInstance(){
        return nullTransaction;
    }
    @Override
    public Money doTransaction(Account account) {
        return new Money(0);
    }

    @Override
    public String transactionInformation() {
        return "Transaction Information Is Unavailable";
    }

    @Override
    public String stringDate() {
        return "No Date Available";
    }

    @Override
    public Money money() {
        return new Money(0);
    }
}
