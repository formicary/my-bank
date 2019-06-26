package com.abc;

public class Transfer {

    public static void newTransfer(double amount, Account sender, Account receiver){

        if(amount <= 0.0){
            throw new IllegalArgumentException("error: invalid amount");
        }else if(!sender.hasSufficientFunds(amount)){
            throw new IllegalArgumentException("error: insufficient funds for proposed transfer");
        }else{
            sender.deductFunds(amount);
            receiver.addFunds(amount);

            sender.transactions.add(new Transaction(-amount, Transaction.TRANSFER_OUT));
            receiver.transactions.add(new Transaction(amount, Transaction.TRANSFER_IN));
        }
    }
}
