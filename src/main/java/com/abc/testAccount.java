package com.abc;

import java.util.Date;
/**
 *
 * @author R. Fei
 * This account is for test only: date is added with Transactions to test 
 * the calculation of accumulated interest
 */
class testAccount extends Account{

    void depositwithDate(double amount, Date tDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException
                                           ("Amount must be greater than zero");
        }
        else{
            Transaction trans = new Transaction(amount, tDate);
            this.getTransactions().add(trans);
            double tmp = getBalance();
            tmp += amount;       
            this.getBalancesList().add(new Transaction(tmp, tDate));
        }
    }
    /*This method is for test only: date is added with Transactions to test 
      the calculation of accured interest and no overdraft is allowed */
    void withdrawwithDate(double amount, Date tDate) {

        if (amount <= 0) {
            throw new IllegalArgumentException
                                           ("Amount must be greater than zero");
        }
        else if (amount > getBalance()){
            throw new IllegalArgumentException
                                 ("There is not enough money in this account!");
        }
        else{
            Transaction trans = new Transaction(-amount, tDate);
            this.getTransactions().add(trans);
            double tmp = getBalance();
            tmp -= amount;           
            this.getBalancesList().add(new Transaction(tmp, tDate));
        }
    }
}
