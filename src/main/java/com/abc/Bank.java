package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
    private List<Employee> employees;

    /**
     * Constructor for the class
     */
    public Bank() {
        customers = new ArrayList<Customer>();
        employees = new ArrayList<Employee>();
    }

    /**
     * Function to add new customers to the bank
     * @param customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    /**
     * Function to add employee to the bank
     * @param employee
     */
    public void addEmployee(Employee employee){
    	employees.add(employee);
    }

    /**
     * Function to provide customer summary
     * @return summary of the customers
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }
    
    /**
     * Function which allows only a manager level employee to retrieve details of all the customers
     * @param empDetail
     * @return summary of the customers only for BRANCH_MANAGER
     */
    public String allCustomerSummary(Employee empDetail) {
    	String summary = "";
    	if(employees.size() > 0){
    		for(Employee e : employees){
    			// Checks if the employee is part of the bank
    			if(e.getName().equalsIgnoreCase(empDetail.getName())){
    				// Checks if the employee has the privilege to view customer details
    				if(e.getEmployeeType() == Employee.BRANCH_MANAGER){
    					summary = "Customer Summary";
    					for (Customer c : customers)
    						summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
    				}else{
    					summary = "Only Branch Manager can view the customer details.";
    				}
    			}
    		}
    	}else{
    		summary = "ERROR!!! You are not an employee.";
    	}

    	return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Function to calculate the total interest paid by the bank
     * @return total amount of interest paid
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
    
    /**
     * Function to generate report for the manager on total interest paid by the bank.
     * @param empDetail
     * @return report on total interest paid by the bank only for BRANCH_MANAGER
     */
    public String reportTotalInterestPaid(Employee empDetail) {
    	double total = 0;
    	String summary = "";
    	if(employees.size() > 0){
    		for(Employee e : employees){
    			// Checks if the employee is part of the bank
    			if(e.getName().equalsIgnoreCase(empDetail.getName())){
    				// Checks if the employee has the privilege to view details
    				if(e.getEmployeeType() == Employee.BRANCH_MANAGER){
    		    		summary = "Total Interest Paid by the Bank";
    		    		for(Customer c: customers)
    		                total += c.totalInterestEarned();
    		    		
    		    		summary += " "+total;
    		    	}else{
    		    		summary = "Only Branch Manager can view the total interest paid details.";
    		    	}
    			}
    		}
    	}else{
    		summary = "ERROR!!! You are not an employee.";
    	}
        
        
        return summary;
    }

    /**
     * Function to retrieve the first customer
     * @return first customer name
     */
    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
