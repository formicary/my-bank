
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

public class BankManager extends User{
    
    public BankManager(String name) {
        super(name);
    }
    
    public void addCustomer(Customer customer, Bank bank) {
        bank.addCustomer(customer);
    }
    
    public String getAllCustomers(Bank bank){
        String s = "";
        for(Customer c : bank.getCustomers()){
            s += "Customer name " + c.getName() + "\n";
            s += "Number of Accounts " + c.getAccounts().size()+ "\n\n";
        }
        return s;
    }
    
    public String getTotalInterestPaid(Bank bank){
        double total = 0;
        for(Customer c: bank.getCustomers()){
            total += c.totalInterestEarned();
        }
        return "Total paid by the bank is $" + total;
    }
    
    
        
}

