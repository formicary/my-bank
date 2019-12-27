
import static java.lang.Math.abs;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author batuhan yilmaz
 */

public class Customer extends User{
    private final ArrayList<Account> accounts;
    
    public Customer(String name) {
        super(name);
        accounts = new ArrayList<>();
    }
    
    public Customer createAccount(Account account){
        accounts.add(account);
        return this;
    }
    
    public void deposit(String uid,double amount) {
        Account a = findAccount(uid);
        a.deposit(amount);
    }

    public void withdraw(String uid,double amount) {
        Account a = findAccount(uid);
        a.withdraw(amount);
    }
    
    public void transferFunds(String uidFrom,double amountDeposit, String uidTo){
        Account a = findAccount(uidFrom);
        Account b = findAccount(uidTo);
        
        a.withdraw(amountDeposit);
        b.deposit(amountDeposit);
    }
    
    public String getAllStatements(){
        String s = "Statement for " + this.getName() + "\n\n";
        int totalBalance = 0;
        
        for(Account a : accounts){
            s += getStatement(a);
            totalBalance += a.getBalance();
        }
        s += "TotalBalance $" + totalBalance;
        return s;
    }
    
    public String getStatement(Account account){
        String s = "";
        
        switch(account.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }
        
        for(Transaction t : account.getTransactions()){
            s += "" + t.getType();
            s += " $" + t.getAmount()+ "\n" + "\n";
        }
        s += "Total " + toDollars(account.getBalance()) + "\n" + "\n";
        return s;
    }
    
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts){
            total += a.calculateInterest();
        }
        return total;
    }
    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    
    public Account findAccount(String uid){
        boolean found = false;
        int i = 0;
        while(!found && i < accounts.size()){
            if(accounts.get(i).getUniqueID().equals(uid)){
                found = true;
                return accounts.get(i);
            }
            else{
                i++;
            }
        }
    throw new IllegalArgumentException("Account doesnt exist");
    }

}
