package com.abc.util;

import com.abc.entity.Account;
import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.entity.AccountType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Daily interest calculator utility to calculate the interest earned by each account on a daily basis
 * @author aneesh
 */
public class DailyInterestCalculator {

    /**
     * Calculate the interest that has accrued for an account since it's first deposit to the present day
     * @param account the account to calculate accrued interest of
     * @return the accrued interest of the account from when it was opened to the present day.
     */
    public static BigDecimal interestEarned(Account account) {

        BigDecimal totalInterestEarned = new BigDecimal("0.00");
        BigDecimal accountBalance = new BigDecimal("0.00");

        for(int i = 0; i< account.getTransactions().size(); i++){

            accountBalance = accountBalance.add(account.getTransactions().get(i).getAmount());
            BigDecimal interestPerDay = calculateDailyInterestOfBalance(account, accountBalance);
            if(i == account.getTransactions().size() -1){
                totalInterestEarned = totalInterestEarned.add(interestPerDay.multiply(new BigDecimal(ChronoUnit.DAYS.between(account.getTransactions().get(i).getTransactionDate(),LocalDateTime.now()))));
            }else{
                totalInterestEarned = totalInterestEarned.add(interestPerDay.multiply(new BigDecimal(ChronoUnit.DAYS.between(account.getTransactions().get(i).getTransactionDate(), account.getTransactions().get(i+1).getTransactionDate()))));
            }
        }

        return totalInterestEarned.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private static BigDecimal calculateDailyInterestOfBalance(Account account, BigDecimal accountBalance) {
        BigDecimal year = new BigDecimal("365.00");
        switch(account.getAccountType()){
            case SAVINGS:
                return AnnualInterestCalculator.calculateSavingsInterest(accountBalance).divide(year, 4, RoundingMode.HALF_UP);
            case MAXI_SAVINGS:
                return AnnualInterestCalculator.calculateMaxiSavingsInterest(accountBalance).divide(year, 4, RoundingMode.HALF_UP);
            case MAXI_SAVINGS_ADD:
                return AnnualInterestCalculator.calculateMaxiSavingInterestAdditionalFeature(accountBalance, account).divide(year, 4, RoundingMode.HALF_UP);
            default:
                return AccountType.CURRENT.getFlatRate().multiply(accountBalance).divide(year,4, RoundingMode.HALF_UP);
        }
    }


    public static BigDecimal totalInterestPaid(Bank bank) {
        BigDecimal total = new BigDecimal("0");
        for(Customer customer : bank.getCustomers())
            total = total.add(DailyInterestCalculator.interestEarned(customer));
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal interestEarned(Customer customer) {
        BigDecimal total = new BigDecimal(0);
        for (Account account : customer.getAccounts().values()) {
            total = total.add(DailyInterestCalculator.interestEarned(account));
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
