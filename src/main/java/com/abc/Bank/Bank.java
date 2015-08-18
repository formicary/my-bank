package main.java.com.abc.Bank;

import java.util.ArrayList;
import java.util.List;

import main.java.com.abc.Customer.Customer;

public class Bank implements IBank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer GetCustomer(int index) {
    	Customer result = null;
    	try {
    		result = customers.get(index);
    	}
    	catch ( IndexOutOfBoundsException e ){
    		e.printStackTrace();
    		System.out.format("CUSTOMER INDEX %d NOT EXIST", index);
    	}
    	return result;
    }
    
	public int GetCustomerNum() {
		// TODO Auto-generated method stub
		return customers.size();
	}
	
	public List<Customer> GetCustomers() {
		// TODO Auto-generated method stub
		return customers;
	}
}
