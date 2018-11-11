package src.main.java.com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    private int currentAccount;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }
    public Customer openAccount(Account account){
        accounts.add(account);
        return this;
    }
    //now CREATES the account to be added to the system
    public Customer openAccount(int accountType) {
        if (accountType < 0 || accountType > 2) throw new IndexOutOfBoundsException("account type doesn't exist");
        accounts.add(new Account(accountType));
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public double totalInterestEarnedTestVer(int days) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarnedTestVer(days);
        return total;
    }    
    
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    public String reviewAccounts(){
        //lists all accounts and their current amounts (i.e. skip the transaction details)
        String s; //stores the details of individial accounts
        String all = ""; //stores the details of all accounts
        for (int i = 0; i<accounts.size(); i++){
            s = i + ": ";
            Account temp = accounts.get(i);
            switch(temp.getAccountType()){
                case Account.CHECKING:
                    s += "Checking Account";
                    break;
                case Account.SAVINGS:
                    s += "Savings Account";
                    break;
                case Account.MAXI_SAVINGS:
                    s += "Maxi Savings Account";
                    break;
            }
            s += "; " + toDollars(temp.sumTransactions());
            System.out.println(s);
            all += s + "\n";
        }
        return all;
    }
    public void transfer (double amount, int sourceAccount, int targetAccount){ 
        if (sourceAccount == targetAccount){ //are the accounts the same?
            throw new IllegalArgumentException("both accounts are the same");
        } else if (withinRange(sourceAccount) && withinRange(targetAccount)){ //are they valid account numbers?
            Account temp = accounts.get(sourceAccount);
            Account target = accounts.get(targetAccount);        
            if (amount > temp.sumTransactions()){ //cannot transfer more money out of an account than it has
                throw new IllegalArgumentException("cannot afford this transfer");
            } else {
                try {
                    temp.withdraw(amount);
                    target.deposit(amount);
                } catch (IllegalArgumentException e) { //if the withdrawal is invalid, no deposit will be made
                    e.printStackTrace();
                    return;
                }
            }
        } else {
            throw new IndexOutOfBoundsException("one or both of these accounts don't exist");
        }
    }
    
    //does this account number represent a valid account, i.e. is it within the List?
    private boolean withinRange(int accountNo){
        if (accountNo >= 0 && accountNo < accounts.size()) return true;
        return false;
    }
    
    //withdraw and deposit to bank accounts directly from the customer object (also useful for testing)
    public void deposit(double amount, int accountNo){
        if (withinRange(accountNo)) accounts.get(accountNo).deposit(amount);
        else throw new IndexOutOfBoundsException("account doesn't exist");
    }
    public void withdraw(double amount, int accountNo){
        if (withinRange(accountNo)) accounts.get(accountNo).withdraw(amount);
        else throw new IndexOutOfBoundsException("account doesn't exist");
    }
}
