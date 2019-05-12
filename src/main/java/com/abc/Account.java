package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account {

    private final int accountType;
    private Date accountDate;

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    public final List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountDate = DateProvider.getInstance().now();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero!");
        } else {
            transactions.add(new Transaction(amount, Transaction.DEPOSIT));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero!");
        } else {
            transactions.add(new Transaction(-amount, Transaction.WITHDRAWAL));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;

                //case SUPER_SAVINGS:
                //if (amount <= 4000)
                // return 20;
            case MAXI_SAVINGS:
                DateProvider dateProvider = new DateProvider();
                Date now = dateProvider.now();

                for(int i = transactions.size() - 1; i >= 0; i--){
                    if (transactions.get(i).getTransactionalType() == Transaction.WITHDRAWAL) {
                        Date lastTransactionDate = transactions.get(i).getTransactionDate();
                        Date dateBefore10Days = dateProvider.oldDate(now,-10);

                        if(lastTransactionDate.before(dateBefore10Days)){
                            return amount * 0.05;
                        }else{
                            return amount * 0.001;
                        }
                    }
                }
                Date dateBefore10Days = dateProvider.oldDate(now, -10);
                if(accountDate.before(dateBefore10Days)){
                    return amount * 0.05;
                }else{
                    return amount * 0.001;
                }
            default:
                return amount * 0.001;
        }
    }


    public void setAccountDate(Date newDate){
        accountDate = newDate;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
}
