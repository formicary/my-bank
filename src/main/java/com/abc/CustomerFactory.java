package com.abc;

import java.util.Date;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class CustomerFactory implements Factory<Customer,String, Date> {

	/**
	 * @param name the name of the customer
	 * @param d the date the customer joined
	 */
	public Customer create(String name, Date d) {
		return new RegularCustomer(name, d);
	}

}
