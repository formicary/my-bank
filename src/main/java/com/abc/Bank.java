package com.abc;

import java.util.ArrayList;
import java.util.List;

import static com.abc.shared.Methods.toDollars;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String getCustomerSummary() {
        String summary = "Customer Summary";

        if(customers.isEmpty()){
            summary += "\n- No customer accounts!";
        }

        for (Customer c : customers) {
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }

        return summary;
    }

    public String getInterestSummary(){
        String summary = "Interest Summary ";
        int numberOfAccounts = 0;
        double total = 0;

        for(Customer customer: customers){
            numberOfAccounts += customer.getNumberOfAccounts();
            total += customer.getTotalInterestEarned();
        }

        summary += "(" + format(numberOfAccounts, "account") + ")";
        summary += "\nInterest Paid: " +  toDollars(total);

        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
}
