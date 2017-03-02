package com.abc;

/**
 * Class to add new Employees along with their designation to the bank.
 * @author abhinav.a.mehrotra
 *
 */
public class Employee {
	public static final int BRANCH_MANAGER = 0;
	public static final int CASHIER = 1;
	public static final int LOAN_OFFICER = 2;
	
	private final int employeeType;
	private String name;
	
	/**
	 * Constructor for the class
	 * @param name
	 * @param employeeType
	 */
	public Employee(String name,int employeeType) {
        this.name = name;
        this.employeeType = employeeType;
    }
	
	// Getter Methods
	public String getName() {
        return name;
    }
	
	public int getEmployeeType() {
        return employeeType;
    }
	
}
