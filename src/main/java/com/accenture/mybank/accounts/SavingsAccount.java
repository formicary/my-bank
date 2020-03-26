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


public class SavingsAccount extends Account {
	//0.2% interest rate
    public SavingsAccount() {
        this.transactions = new ArrayList<Transaction>();
    }

    @Override
    public double calculateInterest(double amount, Date date) {

        if(CommonUtil.isGreaterThanThousand(amount)){
            return (CommonUtil.savingsAccountNextThousandInterestRate/CommonUtil.noOfDays);
        }
        else{
            return (CommonUtil.savingsAccountFirstThousandInterestRate/CommonUtil.noOfDays);
        }
    }

    @Override
    public String getType() {
        return CommonUtil.savingsAccountType;
    }
}
