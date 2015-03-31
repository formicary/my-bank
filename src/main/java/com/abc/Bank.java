package com.abc;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author R. Fei
 */
class Bank {
    
    private final List<Customer> customers;
    private final PrintCustomerSummary Printer;
    
    Bank() {
        this.customers = new ArrayList<>();
        this.Printer = new PrintCustomerSummary(customers);
    }

    void addCustomer(Customer customer) {
        customers.add(customer);
    }
    /*
     * Print customers with the number of accounts under their name:
     * " Customer Summary
     *   - John (2 accounts)
     *   - Name (n accounts) " 
     */
    public String customerSummary() {
        if ( null != Printer )
            return Printer.PrintOut();
        else
            return "Bad Printer Error";
    }
    /* Total interest accured from all bank accounts of all customers */
    double totalInterestPaid() {
        double total = 0.0;
        total = customers.stream().map((c) -> c.totalInterestEarned()).
                     reduce(total, (accumulator, _item) -> accumulator + _item);
        return total;
    }
    
    public String reportTotalInterestPaid(){
        if ( null != Printer )
            return Printer.PrintMoney(totalInterestPaid());
        else
            return "Bad Printer Error";
    }

    String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
            return "Error";
        }
    }
    /* Required by Printing Customer Summary */
    List<Customer> getCustomers(){
        return customers;
    }
    /***************************************************************************
     * Nested class for Print() method.
     **************************************************************************/
    private class PrintCustomerSummary implements Printer{
    
        private final List<Customer> customers;
        private final Formatter format;

        private PrintCustomerSummary(List<Customer> customers){
            this.customers = customers;
            this.format = new Formatter();
        }
        @Override
        public String PrintOut(){
            return this.customerSummary();
        }
        private String PrintMoney(double amount){
            if( null != format)
                return format.toDollars(amount);
            else
                return "Missed Formatting Information";
        }
        /*
         * Print customers with the number of accounts under their name:
         * " Customer Summary
         *   - John (2 accounts)
             - Name (n accounts) " 
         */
        private String customerSummary() {       
            if ( null != customers && !customers.isEmpty()){
                String summary = "Customer Summary";
                summary = customers.stream().map((c) -> 
                        "\n - " + c.getName() + 
                        " (" + format.format(c.getNumberOfAccounts(), "account") + ")").
                        reduce(summary, String::concat);
                return summary + "\n";
            }
            else
                return "No Customer Found!";       
        }
    }  
}
