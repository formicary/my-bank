package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
    private List<Account> accounts;

    public Bank() {
        customers = new ArrayList<Customer>();
        accounts = new ArrayList<Account>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        addAccounts(customer);
    }
    
    public void addAccounts(Customer customer) {
    	for(Account a: customer.getAccounts()) {
    		a.setAccountNo(generateAccountNo());
    		accounts.add(a);
    	}
    }
    
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : getCustomers())
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: getCustomers())
            total += c.totalInterestEarned();
        return total;
    }

//    public String getFirstCustomer() throws Exception {
//    	try {
//	    	if (getCustomers().size() > 0) {
//	            for(int i=0; i<getCustomers().size(); i++) {
//	            	return getCustomers().get(i).getName();
//	            }  		
//	    	}
//	    	else {
//	    		return "Error retrieving name of first customer";
//	    	}
//        } catch (Exception e){
//            e.printStackTrace();
//            return "Error retrieving name of first customer";
//        }
//    }
    
    public int generateAccountNo() {
    	return accounts.size()+1;
    }
    
    public void withdrawFrom(int accountNo, double amount) {
    	for(int i=0; i<accounts.size();i++) {
    		if(accountNo == accounts.get(i).getAccountNo()) {
    			accounts.get(i).withdraw(amount);
    			System.out.println("withdrawn " + amount +
    					" from account no: " + accountNo +
    					" successfully");
    		}
    	}
    }
    public void depositFrom(int accountNo, double amount) {
    	for(int i=0; i<accounts.size();i++) {
    		if(accountNo == accounts.get(i).getAccountNo()) {
    			accounts.get(i).deposit(amount);
    			System.out.println("deposited " + amount + 
    					" to account no: " + accountNo +
    					" successfully");
    		}
    	}
    }
    
    public void transfer(int sourceAccountNo, int destAccountNo, double amount) {
    	withdrawFrom(sourceAccountNo, amount);
    	depositFrom(destAccountNo, amount);
    }
    public List<Customer> getCustomers() {
		return customers;
	}

    public static void main (String [] args) {

	    Bank bank = new Bank();
	    Customer john = new Customer("John");
	    john.openAccount(new Account(Account.CHECKING));
	    Customer anon = new Customer("Anon");
	    anon.openAccount(new Account(Account.CHECKING));
	    john.openAccount(new Account(Account.SAVINGS));
	    bank.addCustomer(john);	    
	    bank.addCustomer(anon);
	    john.getAccounts().get(0).deposit(new Double("500.00"));
	    bank.transfer(1, 2, new Double("100.00"));
	    john.getAccounts().get(1).deposit(new Double("300.00"));
	    bank.transfer(3, 1, new Double("999.00"));
	    System.out.println(john.getStatement());
	    System.out.println(anon.getStatement());
        
    }
}
