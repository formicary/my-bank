/**
 * 
 */
package com.accenture.mybank.accounts;
import java.util.ArrayList;
import java.util.Date;

import com.accenture.mybank.Transaction;
import com.accenture.mybank.utils.CommonUtil;

/**
 * @author anusha.a.avuthu
 *
 */
public class CheckingAccount extends Account {
    //Checking accounts = rate 0.1%

    public CheckingAccount() {
        this.transactions = new ArrayList<Transaction>();
    }

    @Override
    public double calculateInterest(double amount, Date date) {
        return  (CommonUtil.checkingAccountInterestRate/CommonUtil.noOfDays);
    }

    @Override
    public String getType() {
        return CommonUtil.checkingAccountType;
    }
}
