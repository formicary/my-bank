package com.abc.util;

import com.abc.entity.Account;
import com.abc.entity.impl.AccountType;
import com.abc.entity.Bank;
import com.abc.entity.impl.CustomerImpl;
import com.abc.service.TransactionManager;

import java.math.BigDecimal;

public class InterestCalculator {

    public static BigDecimal interestEarned(Account account) {
        BigDecimal accountHolding = TransactionManager.sumTransactions(account);

        switch(account.getAccountType()){
            case SAVINGS:
                if (accountHolding.intValue() <= 1000) {
                    return accountHolding.multiply(AccountType.SAVINGS.getFlatRate());
                }
                else{
                    BigDecimal remainingBalance = getRemaining(accountHolding, "1000");

                    BigDecimal remainingInterest = new BigDecimal(
                            String.valueOf(remainingBalance.multiply(AccountType.SAVINGS.getSecondRate()))
                    );
                    return remainingInterest.add(AccountType.SAVINGS.getFlatRate().multiply(new BigDecimal("1000")));
                }
            case MAXI_SAVINGS:
                if (accountHolding.intValue()  <= 1000) {
                    return accountHolding.multiply(AccountType.MAXI_SAVINGS.getFlatRate());
                }
                else if(accountHolding.intValue() <= 2000){
                    BigDecimal remainingBalance = getRemaining(accountHolding, "1000");
                    BigDecimal remainingInterest = new BigDecimal(
                            String.valueOf(remainingBalance.multiply(AccountType.MAXI_SAVINGS.getSecondRate()))
                    );
                    return remainingInterest.add(AccountType.MAXI_SAVINGS.getFlatRate().multiply(new BigDecimal("1000")));
                }
                else{
                    BigDecimal remainingBalance1 = getRemaining(accountHolding, "1000");
                    BigDecimal remainingBalance2 = getRemaining(accountHolding, "2000");
                    BigDecimal remainingInterest1 = new BigDecimal(
                            String.valueOf(remainingBalance1.multiply(AccountType.MAXI_SAVINGS.getSecondRate()))
                    );
                    BigDecimal remainingInterest2 = new BigDecimal(
                            String.valueOf(remainingBalance2.multiply(AccountType.MAXI_SAVINGS.getThirdRate()))
                    );
                    return remainingInterest1.add(remainingInterest2).add(AccountType.MAXI_SAVINGS.getFlatRate().multiply(new BigDecimal("1000")));

                }
            default:
                return accountHolding.multiply(AccountType.CURRENT.getFlatRate());
        }
    }

    private static BigDecimal getRemaining(BigDecimal accountHolding, String i) {
        return accountHolding.subtract(new BigDecimal(Integer.parseInt(i)));
    }

    public static BigDecimal interestEarned(CustomerImpl customer) {
        BigDecimal total = new BigDecimal(0);
        for (Account account : customer.getAccounts()) {
            total = total.add(InterestCalculator.interestEarned(account));
        }
        return total;
    }

    public static BigDecimal totalInterestPaid(Bank bank) {
        BigDecimal total = new BigDecimal(0);
        for(CustomerImpl customer : bank.getCustomers())
            total = total.add(InterestCalculator.interestEarned(customer));
        return total;
    }

}
