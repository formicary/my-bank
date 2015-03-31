package com.abc;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author R. Fei
 */
class Account {
    
    AccountsEnum accountType;
    private final List<Transaction> transactions;
    private final List<Transaction> BalancesList;
    private PrintTransactions Printer;
    
    Account() {
        this.accountType = AccountsEnum.VANILLA;
        this.BalancesList = new ArrayList<>();
        this.transactions = new ArrayList<>();    
    }
    
    /* The day of the Transaction is the current day */
    void deposit(double amount) {
        if (amount <= 0) {
            System.err.println("Amount must be greater than zero");
            throw new IllegalArgumentException();
        } 
        else {
            transactions.add(new Transaction(amount));
            double tmp = this.getBalance();
            tmp += amount;
            BalancesList.add(new Transaction(tmp));
        }
    }
    /* The day of the Transaction is the current day
     * No overdraft is allowed */
    void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        else if (amount > getBalance()){
            throw new IllegalArgumentException("There is not enough money in this account!");
        }
        else {
            transactions.add(new Transaction(-amount));
            double tmp = getBalance();
            tmp -= amount;
            BalancesList.add(new Transaction(tmp));
        }
    }
    
    void TransferMoney(double amount, Account destination){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } 
        else {
            this.withdraw(amount);
            try {
                destination.deposit(amount);
            } catch (Exception e){
                this.deposit(amount);
                throw new IllegalArgumentException("IndexOutOfBoundsException: " + e.getMessage());
            }
        }
    }
    
    double getBalance(){
        double balance = 0.0;
        if( null != BalancesList){
            if (!BalancesList.isEmpty()){
                int index = getBalancesList().size()-1;
                balance = getBalancesList().get(index).amount;}
        }    
        return balance;
    }
    
    List<Transaction> getBalancesList(){
        return this.BalancesList;
    }
    
    List<Transaction> getTransactions(){
        return this.transactions;
    }
    
    double interestEarned(){
        return 0.0;
    };
    
    public String Print(){
        this.Printer = new PrintTransactions(transactions, accountType);
        if ( null != Printer )
            return Printer.PrintOut();
        else
            return "Bad Printer Error";
    }
    /***************************************************************************
     * Nested class for Print() method.
     **************************************************************************/
    private class PrintTransactions implements Printer {
    
        private final List<Transaction> transactions;
        private final AccountsEnum accountType;
        private final Formatter format;

        private PrintTransactions(List<Transaction> transactions, 
                                                      AccountsEnum accountType){
            this.transactions = transactions;
            this.accountType = accountType;
            this.format = new Formatter();
        }
        @Override
        public String PrintOut(){
            return this.formatAccount();
        }  
        /* Format statement of one account */
        private String formatAccount(){
            String s = formatAccountTypes(accountType) + "\n";
            if( (null != transactions) && !transactions.isEmpty()){
                s = transactions.stream().map((t) -> 
                        formatTransaction(t) + "\n").reduce(s, String::concat);
            }
            s += "Total " + format.toDollars(getBalance()) + "\n";
            return s;
        }

        private String formatAccountTypes(AccountsEnum accountType){
            switch(accountType){
                case CHECKING:
                    return "Checking Account";
                case SAVINGS:
                    return "Savings Account";
                case MAXI_SAVINGS:
                    return "Maxi Savings Account";
                default:
                    return "Vanilla Account";
            }        
        }

        /* Print Formated Transactions, for example:
           Transaction(1000.0) -->   "  deposit $1,000.00\n"
           Transaction(-200.0) -->   "  withdrawal $200.00\n"
        */
        private String formatTransaction(Transaction t){
            if(null != t)
                return "  " + (t.amount < 0 ? "withdrawal" : "deposit") + 
                                               " " + format.toDollars(t.amount);
            else
                return "";
        }
    }//End of nested PrintTransactions class   
}//End of Account class
