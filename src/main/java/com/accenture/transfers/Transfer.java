package com.accenture.transfers;

import com.accenture.accounts.Account;

public abstract class Transfer {

    private Account accountFrom;
    private Account accountTo;

    public Transfer(Account accountFrom, Account accountTo){
        this.accountFrom=accountFrom;
        this.accountTo=accountTo;
    }

    public void transferMoney(double amount){
        double amountPlusFees;
        amountPlusFees = amount + applicableFees();
        accountFrom.withdraw(amountPlusFees);
        accountTo.deposit(amount,"Transfer");
    }

    protected abstract double applicableFees();

}
