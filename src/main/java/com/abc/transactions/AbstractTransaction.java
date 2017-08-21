package com.abc.transactions;


import com.abc.accounts.Account;
import com.abc.utilities.Money;

/**
 * Created by vahizan on 07/08/2017.
 */
public abstract class AbstractTransaction {
    public abstract Money doTransaction(Account account);
    public abstract String transactionInformation();
    public abstract String stringDate();
    public abstract Money money();

}
