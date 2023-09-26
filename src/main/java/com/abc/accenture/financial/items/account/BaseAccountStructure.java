package com.abc.accenture.financial.items.account;

import com.abc.accenture.financial.items.Transaction;

import java.util.ArrayList;
import java.util.List;

abstract class BaseAccountStructure {

    protected final List<Transaction> transactions;

    protected BaseAccountStructure() {
        this.transactions = new ArrayList<>();
    }
}
