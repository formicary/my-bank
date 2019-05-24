package com.accenture.transfers;

import com.accenture.Customer;
import com.accenture.accounts.Account;

public class TransferContext {

    private Transfer transfer;

    public TransferContext(Account accountFrom, Account accountTo) throws Exception {
        chooseTransferType(accountFrom,accountTo);
    }

    public void transfer(double amount){
        this.transfer.transferMoney(amount);
    }

    private void chooseTransferType(Account accountFrom, Account accountTo) throws Exception {
        for(Customer customerFrom : accountFrom.getCustomers()){
            for(Customer customerTo : accountTo.getCustomers()){
                if(customerFrom.getName().equals(customerTo.getName())){
                    transfer = new TransferInterAccounts(accountFrom,accountTo);
                    return ;
                }
            }
        }
        throw new Exception("Feature unavailable");
    }
}
