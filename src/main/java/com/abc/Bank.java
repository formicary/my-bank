package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
	public List<Interest> interests;
	boolean interestChk = true;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
		try {
            customers = null;
            String summary = "Customer Summary";
			for (Customer c : customers)
				summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
			return summary;
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }        
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

	if (interestChk == true){
		public double totalInterestPaid() {
			double total = 0;
			for(Customer c: customers)
				total += c.totalInterestEarned();
			interests.add(new Interest());
			return total;
		}
		interestChk = checkInterestCalc();
	}


    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }

	public boolean checkInterestCalc(){
		Calendar prevDate  = Calendar.getInstance();
		prevDate.add(Calendar.DAY_OF_MONTH, -1).
		if (interests.size > 0){
			if (prevDate == interests.end){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
}
