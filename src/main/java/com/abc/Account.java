package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Account {
    enum AccountType{
        CHECKING("Checking Account"),
        SAVINGS("Savings Account"),
        MAXI_SAVINGS("Maxi Savings Account");

        private String accountTypeName;

        public String getAccountTypeName()
        {
            return accountTypeName;
        }

        AccountType(String accountTypeName) {
            this.accountTypeName=accountTypeName;
        }
    }

    private final AccountType accountType;
    private List<Transaction> transactions;
    private LocalDate dateOfLastInterestsEarned;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransaction(new Transaction(amount,"deposit"));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransaction(new Transaction(-amount,"withdraw"));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        double interest=0;
        long daysFromLastInterestEarned=ChronoUnit.DAYS.between(dateOfLastInterestsEarned, LocalDate.now());
        while(daysFromLastInterestEarned>0) {
            switch (accountType) {
                case SAVINGS:
                    if (amount <= 1000)
                        interest += amount * 0.001;
                    else
                        interest += 1 + (amount - 1000) * 0.002;
                    break;
                case CHECKING:
                        interest += amount * 0.001;
                    break;

                case MAXI_SAVINGS:
                    if (transactions.stream().filter(Transaction::isThereTenDaysWithdraw).count() > 0)
                        interest += amount * 0.05;
                    else
                        interest += amount * 0.001;
                    break;

            }
            amount+=interest;
            daysFromLastInterestEarned--;
        }
        dateOfLastInterestsEarned=LocalDate.now();
        return interest;

    }

    public double sumTransactions() {
        return transactions.stream().mapToDouble(Transaction::getTransactionAmount).sum();
    }

    public AccountType getAccountType() {
        return accountType;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setDateOfLastInterestsEarned(LocalDate date)
    {
        this.dateOfLastInterestsEarned=date;
    }

    public void addTransaction(Transaction transaction)
    {
        transactions.add(transaction);
    }

}
