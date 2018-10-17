package com.abc.Accounts;

import com.abc.Transaction;
import com.abc.util.Money;

import java.util.List;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        super(Account.CHECKING);
    }

    public Money interestEarned() {
        Money amount = sumTransactions();
//        switch(accountType){
//            case SAVINGS:
//        if (amount.compareTo(new Money("1000")) <= 0) // if amount is less than or equal to 1000
//            return (Money) amount.multiply(new Money("0.001"));
//        else{
//            Money one = new Money("1"); // 1 from 1000*0.1 (interest on the first 1000
//            return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
//            case MAXI_SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
//            default:
                return (Money) amount.multiply(new Money("0.001"));

    }
}
