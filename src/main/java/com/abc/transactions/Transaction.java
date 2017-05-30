package com.abc.transactions;

import com.abc.exceptions.ExceedsNegativeBalanceException;
import com.abc.exceptions.NonPositiveAmountException;

import java.util.Date;

public interface Transaction {
    Date getTransactionDate();

    double getAmount();

    double executeTransaction() throws ExceedsNegativeBalanceException, NonPositiveAmountException;
}
