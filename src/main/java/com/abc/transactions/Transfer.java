package com.abc.transactions;

import com.abc.accounts.Account;
import com.abc.constants.AccountConstantsNew;
import com.abc.utilities.DateProvider;
import com.abc.utilities.Money;

/**
 * Created by vahizan on 18/08/2017.
 */
public class Transfer  {
    private Money money;
    private String transactionDate;
    public Transfer(Money amount){
        this.money =amount;
        //set deposit date
        transactionDate= DateProvider.getInstance().generateDate();
    }
    public String transactionInformation(AccountConstantsNew from, AccountConstantsNew to){
        StringBuilder sb = new StringBuilder();
        sb.append("Transfer From: " )
                .append(accountString(from))
                .append("\n")
                .append("To: ")
                .append(accountString(to))
                .append("\n")
                .append("Amount: ")
                .append(amount())
                .append("\n")
                .append("Date: ")
                .append(stringDate());
        return sb.toString();
    }
    public String accountString (AccountConstantsNew accountType){
        if(accountType==AccountConstantsNew.CHECKING){
            return "Checking Account";
        }else if(accountType==AccountConstantsNew.MAXI_SAVINGS){
            return "Maxi-Savings Account";
        }else if(accountType==AccountConstantsNew.SAVINGS){
            return "Savings Account";
        }
        return "Account Information Unavailable";
    }
    public String stringDate(){
        return transactionDate;
    }
    public Money amount() {
        return money;
    }
    public Money moneyTo(Account accountTo){
        Deposit deposit = new Deposit(money);
        return deposit.doTransaction(accountTo);
    }
    public Money moneyFrom(Account accountFrom){
        Withdraw withdraw = new Withdraw(money);
        return withdraw.doTransaction(accountFrom);
    }
}
