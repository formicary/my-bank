package com.abc.organisation;

import com.abc.constants.AccountConstantsNew;
import com.abc.person.Customer;
import com.abc.person.Employee;
import com.abc.rates.*;
import com.abc.utilities.Days;

/**
 * Created by vahizan on 07/08/2017.
 */
public class Bank {
    private Customers customers;
    private Workers employees;
    public Bank() {
        customers = new Customers();
        employees=new Workers();
    }
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    public void addEmployee(Employee employee){
        employees.add(employee);
    }
    public Interest createInterest(InterestRate rate, Band interestBand){
        return new Interest(rate,interestBand);
    }
    public void payInterest(Interest interest,AccountConstantsNew accountType){
        for(Customer customer:customers.all()){
            customer.gainInterest(interest, accountType);
        }
    }
    public void payInterestBasedOnWithdrawal(Interest interest, Interest withdrawalInterest, AccountConstantsNew accountType, Days days){
        for(Customer customer:customers.all()){
            applyWithdrawalInterest(withdrawalInterest,days,accountType,customer);
        }
    }
    private void applyWithdrawalInterest(Interest withdrawalInterest,Days days,AccountConstantsNew accountType,Customer customer){
        boolean withdrawal= customer.withdrawalInPeriod(days, accountType);
        if(withdrawal){
            customer.gainInterest(withdrawalInterest,accountType);
        }
    }
    public Customers customerInformation(){
        return customers;
    }
    public int customerCount() {
        return customers.size();
    }
    public int employeeCount(){
        return employees.size();
    }
}
