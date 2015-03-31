package com.abc;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author R. Fei
 */
class Customer {
    private final String name;
    private final List<Account> accounts;
    private PrintStatements Printer;

    Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>(); 
    }

    String getName() {
        return name;
    }

    void openAccount(Account account) {
        accounts.add(account);
    }

    int getNumberOfAccounts() {
        return accounts.size();
    }
    /* Transfer money between different accounts of the customer
     * No overdraft is allowed
     */
    void transferBetweenAccounts(int indexFrom, int indexTo, double amount){
        if (amount <= 0){
            throw new IllegalArgumentException
                                          ("amount must be greater than zero!");
        }
        if (indexTo == indexFrom){
            throw new IllegalArgumentException
                ("Please select another account to transfer your money!");
        } 
        try{
            if (accounts.get(indexFrom).getBalance() < amount) {
                throw new IllegalArgumentException
                                 ("There is not enough money in this account!");
            }
            accounts.get(indexFrom).TransferMoney(amount, accounts.get(indexTo));
        }
        catch(Exception e){
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        }
    }
    /* Total interest accured from all bank accounts of this customer */
    double totalInterestEarned() {
        double total = 0;
        total = accounts.stream().map((a) ->
                a.interestEarned()).reduce(total, (accumulator, _item) ->
                    accumulator + _item);
        return total;
    }
    /* Required by Printing Statements */
    List<Account> getAccounts(){
        return accounts;
    }

    public String Print() {
        this.Printer = new PrintStatements(accounts, name);
        if ( null != Printer )
            return Printer.PrintOut();
        else
            return "Bad Printer Error";
    }
    /***************************************************************************
     * Nested class for Print() method.
     **************************************************************************/
    private class PrintStatements implements Printer{
    
        private final List<Account> accounts;
        private final String name;
        private final Formatter format;

        private PrintStatements(List<Account> accountList, String name){
            this.accounts = accountList;
            this.name = name;
            this.format = new Formatter();
        }
        @Override
        public String PrintOut(){
            return this.formatCustomer();
        }
        /* Print the statement of all bank accounts under this customer:
         * " Statement for Henry
         *   (blank line)                    
         *   Checking Account
         *     deposit $100.00\n"+
         *   Total $100.00\n"+
         *   (2 blank lines)
         *   Savings Account\n"+
         *     deposit $1,000.00
         *   Total $1,000.00\n"+
         *   (1 blank line)
         *   Total In All Accounts $1,100.00  "
         */
        private String formatCustomer(){
            if( null != accounts && !accounts.isEmpty() ){
                String s = "Statement for " + name + "\n";
                double total = 0.0;
                for (Account a : accounts) {
                    double balance = a.getBalance();
                    s += "\n" + a.Print() + "\n";
                    total += balance;
                }
                s += "Total In All Accounts " + format.toDollars(total);
                return s;
            }
            else
                return "No Account is Related to " + name;
        }
    }//End of nested PrintStatements class 
}//End of Customer class
