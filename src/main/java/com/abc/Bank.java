import java.util.*;


/* This class implements a really basic bank application.
Many changes have been made compared to the initial version.
Many efforts have been made to keep the architecture in a

 */
public class Bank {
    // Changed this to a HashMap because we have constant time for all the operations which is better compared to the linear time needed
    // for the ArrayList.
    //Also, the bank needs to hold information about the customers and their accounts
    //In real life bank applications those information should be encrypted but this is beyond of the scope
    // of this test.
    private Map<Customer, Set<Account>> customers;
    public Bank() {
        customers = new LinkedHashMap<>();
    }
// A method that adds a customer to the bank's list.
    public void addCustomer(Customer customer)
    { if(customers.containsKey(customer)){
        throw new IllegalArgumentException("Customer already exists");
    }else{
        customers.put(customer,customer.getAccounts());
    }

    }
// A method that deletes a Customer from the bank's list.
    public void deleteCustomer(Customer customer){
        if(customers.containsKey(customer)){
            customers.remove(customer);
        }else{
            throw new IllegalArgumentException("The specified customer does not exist");
        }
    }

    @Override
    public String toString() {
        return "Bank{" +
                "customers=" + customers +
                '}';
    }

    // Method which prints a statement for the customer
    public String customerSummary() {
        Set <Customer> keys = customers.keySet();
        String summary = "Customer Summary";
        for (Customer c : keys)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

// Method which calculates the total interest that the bank has to pay to it's customers
    // There is the option to calculate the daily or the yearly interest
    public double totalInterestToBePaid(Account.InteType type) {
        if (type.equals(Account.InteType.DAILY)) {
            return customers.keySet().stream().mapToDouble(Customer::totalInterestEarnedDaily).sum();
        } else {
            return customers.keySet().stream().mapToDouble(Customer::totalInterestEarnedYearly).sum();
        }
    }

    //Method used to transfer the interest earned to each customer's bank account
    public void transferInterestEarnedToEachCustomer(){
        customers.keySet().stream().forEach(Customer::transferInterestEarnedOfCustomer);
    }

//This method finds and returns the first customer of the bank
    //The method is not used anywhere at the moment but I left it here for future use
    public String getFirstCustomer() {
        try {
            return customers.keySet().stream().findFirst().get().getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }

    public Map<Customer,Set<Account>> getCustomers(){
        return customers;
    }
}
