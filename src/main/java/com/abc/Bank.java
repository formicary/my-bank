import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author batuhan yilmaz
 */

public class Bank {
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }
    
    
    
}
