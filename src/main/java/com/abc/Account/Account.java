package com.abc.Account;

import com.abc.Bank;
import com.abc.DateProvider;
import com.abc.Exception.IllegalInterestException;
import com.abc.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {


    public enum AccountType {
        CHECKING, SAVINGS, MAXI_SAVINGS
    }

    private double balance;
    protected final AccountType accountType;
    private List<Transaction> transactions;

    public Account(AccountType type) {
        this.balance = 0.0;
        this.accountType = type;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            balance+=amount;
            transactions.add(new Transaction(amount, Transaction.TransctionType.DEPOSIT, DateProvider.getInstance().now()));
        }
    }

    public void recieveTransfer(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            balance+=amount;
            transactions.add(new Transaction(amount, Transaction.TransctionType.TRANSFER, DateProvider.getInstance().now()));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            balance-=amount;
            transactions.add(new Transaction(-amount, Transaction.TransctionType.WITHDRAWAL, DateProvider.getInstance().now()));
    }
}
    public double interestEarned() {
        double amount = 0.0;
        for(Transaction t : transactions){
            if(t.type.equals(Transaction.TransctionType.INTEREST)){
                amount+=t.amount;
            }
        }
        return amount;
    }

    protected double applyInterest(double amount, Date lastWithdrawal){
        double dailyRate = 0.001 / 365;
        return amount * dailyRate;
    }

    public void accrueDailyInterest(){
        if(transactions.size() == 0 | getBalance() <= 0){
            throw new IllegalInterestException("No balance to calculate interest");
        }
        Long today = DateProvider.getInstance().now().getTime();
        double amount = 0.0;
        Date lastWithdrawal = transactions.get(0).transactionDate;
        for(Transaction t : transactions){
            if(t.type.equals(Transaction.TransctionType.INTEREST) &
                    today - t.transactionDate.getTime() < DateProvider.dayInMillis){
                throw new IllegalInterestException("Interest already accrued today");
            }
            if(t.type.equals(Transaction.TransctionType.WITHDRAWAL) &
                    t.transactionDate.after(lastWithdrawal)){
                lastWithdrawal = t.transactionDate;
            }
            amount += t.amount;
        }
        amount = applyInterest(amount, lastWithdrawal);
        Transaction transaction = new Transaction(amount, Transaction.TransctionType.INTEREST, DateProvider.getInstance().now());
        transactions.add(transaction);
        balance+=amount ;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public double getBalance(){return balance;}

    public Transaction getLastTransaction(){
        return transactions.get(transactions.size()-1);
    }

    public Transaction getTransaction(int index){
        return transactions.get(index);
    }

    public int getNumberOfTransactions(){
        return transactions.size();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        StringBuffer builder = new StringBuffer();

        //Translate to pretty account type
        switch(accountType){
            case CHECKING:
                builder.append("Checking Account\n");
                break;
            case SAVINGS:
                builder.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                builder.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            builder.append("  ").append(t.amount < 0 ? "withdrawal" : "deposit").append(" ").append(Bank.toDollars(t.amount)).append("\n");
            total += t.amount;
        }
        builder.append("Total ").append(Bank.toDollars(total));
        return builder.toString();
    }

}
