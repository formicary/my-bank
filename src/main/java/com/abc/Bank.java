/*Edited by: Foyaz Hasnath*/
package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    private DateProvider dateProvider = new DateProvider();
    private Date bankCreationDate;
    private Date businessDate;

    public Bank() {
        customers = new ArrayList<Customer>();
        bankCreationDate = dateProvider.now();
        businessDate = bankCreationDate;//business day set to creation day of bank at inception
        System.out.println("date bank opened: "+dateProvider.dateFormat(bankCreationDate));
    }

    public Date getCurrBusinessDate(){
        return businessDate;
    }
    //to "roll" or move forward business date to simulate some amount of time between bankCreationDate and businessDate
    public void rollBusinessDate(int days){
        System.out.println("before roll date: "+dateProvider.dateFormat(getCurrBusinessDate()));
        Calendar cal = Calendar. getInstance();
        cal.setTime(businessDate);
        cal.add(Calendar.DATE, days);
        businessDate=cal.getTime();
        System.out.println("after roll date: "+dateProvider.dateFormat(getCurrBusinessDate()));
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
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
        for(Customer c: customers)
            total += c.totalInterestEarned(businessDate);
        return total;
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
}
