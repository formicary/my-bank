package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bank implements Iterable<Customer> {
	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		if (customers != null || customer != null) {
			customers.add(customer);
		}
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public Iterator<Customer> iterator() {
		return customers.iterator();
	}

	// Compares bank's customers against another bank's customers.
	public int compareTo(Bank other) {
		return ((Bank) this.customers).compareTo((Bank) other.customers);
	}

	// Overriding the default toString representation of the Bank class.
	@Override
	public String toString() {
		return "Bank [customers=" + customers + "]";
	}

}
