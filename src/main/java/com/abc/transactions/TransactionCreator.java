package com.abc.transactions;


import com.abc.constants.TransactionConstantsNew;
import com.abc.utilities.Money;

import static com.abc.constants.TransactionConstants.*;
/**
 * Created by vahizan on 08/08/2017.
 */
public class TransactionCreator {

    public static AbstractTransaction createTransaction(TransactionConstantsNew type,Money money){
        if(type==TransactionConstantsNew.PAY_INTEREST){
            return new InterestDeposit(money);
        }else if(type==TransactionConstantsNew.WITHDRAW){
            return new Withdraw(money);
        }else if(type==TransactionConstantsNew.DEPOSIT){
            return new Deposit(money);
        }
        return NullTransaction.getInstance();
    }
}
