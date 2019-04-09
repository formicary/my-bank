package com.mybank.service;

import com.mybank.entity.Account;
import com.mybank.entity.Transaction;
import com.mybank.util.CurrencyConverter;
import com.mybank.util.DateProvider;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AccountService {

    private final boolean checkAllTransactions = true;

    private static final double YEARS = 1.0;

    /**
     *
     * @param account
     * @param amount
     */
    public void deposit(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        account.getTransactions().add(new Transaction(amount));
    }

    /**
     *
     * @param account
     * @param amount
     */
    public void withdraw(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be less than zero");
        }
        if(sumTransactions(account) < amount) {
            throw new IllegalArgumentException("not enough money on account");
        }
        account.getTransactions().add(new Transaction(-amount));
    }

    /***
     *
     * @param fromAccount
     * @param toAccount
     * @param amount
     */
    public void transfer(Account fromAccount, Account toAccount, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be larger than zero");
        }

        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
    }

    public double compoundInterest(double principal, double interest, double nOfYearlyComp, double years) {
        double amountWithInterest = principal * Math.pow(1.0 + (interest/nOfYearlyComp), (nOfYearlyComp*years));
        return Math.round((amountWithInterest - principal) * 100.0) / 100.0;
    }

    /**
     *
     * @param account
     * @return
     */
    public double interestEarned(Account account) {
        double amount = sumTransactions(account);
        if(amount <= 0.0) {
            return 0.0;
        }
        double interestTotal = 0.0;
        switch(account.getAccountType()){
            case CHECKING: {
                interestTotal = compoundInterest(amount, 0.001, account.getNoYearlyCompounded(), YEARS);
            }
            break;
            case SAVINGS: {
                double interest1 = compoundInterest(Math.min(amount, 1000.0), 0.001, account.getNoYearlyCompounded(), YEARS);
                double interest2 = 0.0;
                if (amount > 1000.0) {
                    interest2 = compoundInterest(amount - 1000.0, 0.002, account.getNoYearlyCompounded(), YEARS);
                }
                interestTotal = interest1 + interest2;
            }
            break;
            case MAXI_SAVINGS: {
                Date lastWithdrawal = null;
                for(Transaction transaction : account.getTransactions()) {
                    if(transaction.getAmount() < 0) {
                        lastWithdrawal = transaction.getTransactionDate();
                        break;
                    }
                }
                long lastWithdrawalDays = -1;
                if(lastWithdrawal != null) {
                    lastWithdrawalDays = TimeUnit.DAYS.convert(DateProvider.getInstance().now().getTime() -
                                                                lastWithdrawal.getTime(), TimeUnit.MILLISECONDS);
                }
                double interest = 0.001;
                if(lastWithdrawal == null || lastWithdrawalDays > 10) {
                    interest = 0.05;
                }
                interestTotal = compoundInterest(amount, interest, account.getNoYearlyCompounded(), YEARS);
            }
            break;
            default:
                throw new IllegalArgumentException("Unknown account type: " + account);
        }

        return interestTotal;
    }

    /**
     *
     * @param account
     * @return
     */
    public double sumTransactions(Account account) {
        return checkIfTransactionsExist(account);
    }

    private double checkIfTransactionsExist(Account account) {
        double amount = 0.0;
        for (Transaction t: account.getTransactions()) {
            if(checkAllTransactions && t.getAmount() == 0) {
                throw new IllegalArgumentException("Invalid transaction. Amount cannot be 0.");
            }
            amount += t.getAmount();
        }
        return amount;
    }

    /**
     *
     * @param a
     * @return
     */
    public String statementForAccount(Account a) {
        String s = a.getAccountType().getName() + "\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + CurrencyConverter.toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + CurrencyConverter.toDollars(total);
        return s;
    }
}
