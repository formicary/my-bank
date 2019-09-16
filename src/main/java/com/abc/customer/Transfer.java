package com.abc.customer;

import com.abc.Money;
import com.abc.Transaction;
import com.abc.account.Account;
import com.abc.exception.InsufficientBalanceException;

public class Transfer {
    private Account recieving;
    private Account paying;
    private Money amount;

    public Transfer(Account paying, Account recieving, Money amount){
        this.recieving = recieving;
        this.paying = paying;
        this.amount = amount;
    }

    private Transaction getRecievingAccountTransaction(){
        return new Transaction(amount);
    }

    private Transaction getPayingTransaction(){
        return new Transaction(new Money("0").subtract(amount));
    }

    public void processTransfer() throws InsufficientBalanceException, Error{
        try {
            paying.processTransaction(getPayingTransaction());
        } catch (InsufficientBalanceException e) {
            throw new InsufficientBalanceException("Insufficient funds in payment account to transfer");
        }
        try {
            recieving.processTransaction(getRecievingAccountTransaction());
        } catch (InsufficientBalanceException e){
            throw new Error("Unexpected Insufficent Funds Exception for deposit");
        }
    }
}
