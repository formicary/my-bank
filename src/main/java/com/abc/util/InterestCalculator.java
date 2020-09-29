package com.abc.util;

import com.abc.entity.Account;
import com.abc.entity.AccountType;
import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.service.TransactionManager;

import java.math.BigDecimal;

public class InterestCalculator {

    public static BigDecimal interestEarned(Account account) {
        BigDecimal accountHolding = TransactionManager.sumTransactions(account);

        switch(account.getAccountType()){
            case SAVINGS:
                if (accountHolding.intValue() <= 1000)
                    return accountHolding.multiply(AccountType.SAVINGS.getFlatRate());

            case MAXI_SAVINGS:
                if (accountHolding.intValue()  <= 1000) {
                    return accountHolding.multiply(new BigDecimal(0.02));
                }
            default:
                return accountHolding.multiply(new BigDecimal(0.001));
        }
    }

    public static BigDecimal interestEarned(Customer customer) {
        BigDecimal total = new BigDecimal(0);
        for (Account account : customer.getAccounts()) {
            total = total.add(InterestCalculator.interestEarned(account));
        }
        return total;
    }

    public static BigDecimal totalInterestPaid(Bank bank) {
        BigDecimal total = new BigDecimal(0);
        for(Customer customer : bank.getCustomers())
            total = total.add(InterestCalculator.interestEarned(customer));
        return total;
    }

}
