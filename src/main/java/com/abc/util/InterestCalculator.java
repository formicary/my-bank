package com.abc.util;

import com.abc.entity.Account;
import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.entity.impl.AccountType;
import com.abc.service.TransactionManager;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Interest calculator utility to calculate the interest earnt by each account.
 * @author aneesh
 */
public class InterestCalculator {

    public static BigDecimal interestEarned(Account account) {
        BigDecimal accountHolding = TransactionManager.sumTransactions(account);
        BigDecimal interest = null;
        switch(account.getAccountType()){
            case SAVINGS:
                if(accountHolding.intValue() <= 1000) {
                    interest = accountHolding.multiply(AccountType.SAVINGS.getFlatRate());
                    break;
                }
                else{
                    BigDecimal remainingBalance = getRemaining(accountHolding, "1000");

                    BigDecimal remainingInterest = new BigDecimal(
                            String.valueOf(remainingBalance.multiply(AccountType.SAVINGS.getSecondRate()))
                    );
                    interest =  remainingInterest.add(AccountType.SAVINGS.getFlatRate().multiply(new BigDecimal("1000")));
                    break;

                }
            case MAXI_SAVINGS:
                if (accountHolding.intValue()  <= 1000) {
                    interest = accountHolding.multiply(AccountType.MAXI_SAVINGS.getFlatRate());
                    break;

                }
                else if(accountHolding.intValue() <= 2000){
                    BigDecimal remainingBalance = getRemaining(accountHolding, "1000");
                    BigDecimal remainingInterest = new BigDecimal(
                            String.valueOf(remainingBalance.multiply(AccountType.MAXI_SAVINGS.getSecondRate()))
                    );
                    interest = remainingInterest.add(AccountType.MAXI_SAVINGS.getFlatRate().multiply(new BigDecimal("1000")));
                    break;

                }
                else{
                    BigDecimal interest1 = AccountType.MAXI_SAVINGS.getFlatRate().multiply(new BigDecimal("1000"));
                    BigDecimal interest2 = AccountType.MAXI_SAVINGS.getSecondRate().multiply(new BigDecimal("1000"));

                    BigDecimal remainingBalance2 = getRemaining(accountHolding, "2000");

                    BigDecimal remainingInterest2 = new BigDecimal(
                            String.valueOf(remainingBalance2.multiply(AccountType.MAXI_SAVINGS.getThirdRate()))
                    );
                    interest = interest1.add(interest2).add(remainingInterest2);
                    break;
                }
            default:
                interest = accountHolding.multiply(AccountType.CURRENT.getFlatRate());
        }
        return interest.setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal getRemaining(BigDecimal accountHolding, String i) {
        return accountHolding.subtract(new BigDecimal(Integer.parseInt(i)));
    }

    public static BigDecimal interestEarned(Customer customer) {
        BigDecimal total = new BigDecimal(0);
        for (Account account : customer.getAccounts()) {
            total = total.add(InterestCalculator.interestEarned(account));
        }
        return total;
    }

    public static BigDecimal totalInterestPaid(Bank bank) {
        BigDecimal total = new BigDecimal("0");
        for(Customer customer : bank.getCustomers())
            total = total.add(InterestCalculator.interestEarned(customer));
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
