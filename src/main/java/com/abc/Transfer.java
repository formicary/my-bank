package com.abc;

public class Transfer {

    // checks if transfer is valid, if so calls a method to perform the transfer
    public static void newTransfer(double amount, Account sender, Account receiver){

        if(amount <= 0.0) {
            throw new IllegalArgumentException("error: invalid amount");

        }else if(!accountsSameOwner(sender, receiver)){
            throw new IllegalArgumentException("error: can only transfer funds between accounts owned by the same customer");
        }else if(!sender.hasSufficientFunds(amount)){
            throw new IllegalArgumentException("error: insufficient funds for proposed transfer");
        }else{
            performTransfer(amount, sender, receiver);
        }
    }

    private static boolean accountsSameOwner(Account sender, Account receiver) {

        return (sender.owner.uniqueID.equals(receiver.owner.uniqueID));

    }

    // simply performs a transfer of funds between accounts, plus creates a transaction record of it
    protected static void performTransfer(double amount, Account sender, Account receiver){

        sender.deductFunds(amount);
        sender.transactions.add(new Transaction(-amount, Transaction.TRANSFER_OUT));

        receiver.addFunds(amount);
        receiver.transactions.add(new Transaction(amount, Transaction.TRANSFER_IN));
    }
}
