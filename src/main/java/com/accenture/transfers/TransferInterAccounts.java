package com.accenture.transfers;

import com.accenture.accounts.Account;

public class TransferInterAccounts extends Transfer {

    public TransferInterAccounts(Account accountFrom, Account accountTo){
        super(accountFrom,accountTo);
    }

    @Override
    protected double applicableFees() {
        return 0.0;
    }
}
