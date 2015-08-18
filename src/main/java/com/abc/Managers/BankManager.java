package main.java.com.abc.Managers;

import java.util.List;

import main.java.com.abc.Bank.IBank;
import main.java.com.abc.Customer.Customer;

/*
 * A manager class that manages the List of customers of a bank.
 * Takes an IBank
 */
public class BankManager{
	
	private List<Customer> customers;
	public BankManager(IBank bank){
		this.customers = bank.GetCustomers();
	}

	public String GetSummary() {
        String summary = "Customer Summary";
        for (Customer c : this.customers)
            summary += "\n - " + c.GetName() + " (" + format(c.GetNumberOfAccounts(), "account") + ")";
        return summary;
    }
    
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number <= 1 ? word : word + "s");
    }
    
    public double GetTotalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.GetTotalInterestEarned();
        return total;
    }

}
