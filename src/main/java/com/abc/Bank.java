package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;

public class Bank implements Common {
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
    /**
     * Generation bank account number
     * @return account no
     */
    public int generateAccountNo() {
    	return accounts.size()+1;
    }
    /**
     * Withdraw amount from an account
     * @param accountNo account to withdraw from
     * @param amount amount to withdraw
     * @return result of transaction
     */    
    public boolean withdrawFrom(int accountNo, double amount) {
    	boolean result = false;
    	for(int i=0; i<accounts.size();i++) {
    		if(accountNo == accounts.get(i).getAccountNo()) {
    			result = accounts.get(i).withdraw(amount);
    			if(result) {
        			System.out.println("withdrawn " + toDollars(amount) +
        					" from account no: " + accountNo +
        					" successfully");    				
    			}
    		}
    	}
    	return result;
    }
    /**
     * Deposit amount into an account
     * @param accountNo account to deposit to
     * @param amount amount to deposit
     * @return result of transaction
     */
    public boolean depositTo(int accountNo, double amount) {
    	boolean result = false;
    	for(int i=0; i<accounts.size();i++) {
    		if(accountNo == accounts.get(i).getAccountNo()) {
    			result = accounts.get(i).deposit(amount);
    			if(result) {
        			System.out.println("deposited " + toDollars(amount) + 
        					" to account no: " + accountNo +
        					" successfully");    				
    			}
    		}
    	}
    	return result;
    }
    /**
     * Transfer between bank accounts
     * @param accountNo account to withdraw from
     * @param amount amount to withdraw
     * @return result of transaction
     */    
    public void transfer(int sourceAccountNo, int destAccountNo, double amount) {
    	boolean validCheck = false;
    	validCheck = withdrawFrom(sourceAccountNo, amount);
    	if(validCheck) {
    		depositTo(destAccountNo, amount);
    	}
    }
    
	@Override
	public String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
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
	    anon.getAccounts().get(0).deposit(new Double("0.00")); // 3
	    // john.getAccounts().get(0).deposit(new Double("500.00")); // 1
	    john.getAccount(1).deposit(new Double("500.00"));
	    bank.transfer(1, 2, new Double("100.00"));
	    
	    bank.transfer(3, 1, new Double("999.00"));
	    
	    
	    System.out.println(john.getStatement());
	    System.out.println(anon.getStatement());
	    System.out.println(bank.customerSummary());
        
    }

}
