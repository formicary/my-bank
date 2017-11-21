package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    //created list for transactions
    public List<Transaction> transactions;
    //initialisation of account variables
    private double account_Balance;
    private string account_Name;

    //created default account balance to be zero and calls on the transacation list on the start up of the account
    Account() {
        transactions = new ArrayList<Transacation>
        this.account_Balance = 0.0;
    }

    public List<Transaction> getTransactions() {
        return Transactions();
    }

    public double getAccountBalance() {
        return account_Balance;
    }


   /* public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;*/
//    public Account(int accountType) {
//        this.accountType = accountType;
//        this.transactions = new ArrayList<Transaction>();
//    }

    //method for deposits to accounts
    public boolean synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.executeTransaction(amount, "deposit");
            return true;
        }

    }

//    public void deposit(double amount) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("amount must be greater than zero");
//        } else {
//            transactions.add(new Transaction(amount));
//        }
//    }

    //method for withdrawls in accounts
    public boolean synchronized void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if (amount > account_Balance) {
                System.out.println("you have insufficient funds to complete this request");
                return false;
            } else {
                this.doTransaction(amount, "withdraw");
                return true;
            }

        }


        //method for transfers between accounts

    public boolean synchronized void transfer(double amount, Account account)

    {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (amount <= account_Balance) {
            this.doTransaction(-amount, "To " + account.getAccountName());
            account.doTransaction(amount, "From" + this.getAccountName());
            return true;
        } else {
            System.out.println("you have insufficient funds to complete this request");
            return false;
        }
    }

    public void getTransaction(double amount, string type) {
        this.account_Balance += amount;
        this.transactions.add(new Transaction(amount, type));

    }

}
//         if (amount <= 0) {
//                throw new IllegalArgumentException("amount must be greater than zero");
//         } else {
//        transactions.add(new Transaction(-amount));
//         }

//    public double interestEarned() {
//        double amount = sumTransactions();
//        switch(accountType){
//        case SAVINGS:
//        if (amount <= 1000)
//        return amount * 0.001;
//        else
//        return 1 + (amount-1000) * 0.002;
////            case SUPER_SAVINGS:
////                if (amount <= 4000)
////                    return 20;
//        case MAXI_SAVINGS:
//        if (amount <= 1000)
//        return amount * 0.02;
//        if (amount <= 2000)
//        return 20 + (amount-1000) * 0.05;
//        if (amount <=0)
//        return amount * 0.05;
//        return 70 + (amount-2000) * 0.1;
//default:
//        return amount * 0.001;
//        }
//        }

//    public double sumTransactions() {
//        return checkIfTransactionsExist(true);
//    }
//
//    private double checkIfTransactionsExist(boolean checkAll) {
//        double amount = 0.0;
//        for (Transaction t : transactions)
//            amount += t.amount;
//        return amount;
//    }

    public void setAccountName() {
        this.account_Name = account_Name;
    }

    public string getAccountName() {
        return this.account_Name;
    }


//    public int getAccountType() {
//        return accountType;
//    }


}
