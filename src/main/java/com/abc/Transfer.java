package com.abc;

public class Transfer {
    
    public static void performTransfer(double amount, Account sender, Account receiver){

        if(amount <= 0.0){
            throw new IllegalArgumentException("error: invalid amount");
        }else if(!sender.hasValidFunds(amount)){
            throw new IllegalArgumentException("error: insufficient funds for proposed transfer");
        }else{
            sender.deductFunds(amount);
            receiver.addFunds(amount);
        }
    }
}
