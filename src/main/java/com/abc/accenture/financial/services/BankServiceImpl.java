package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Bank;
import com.abc.accenture.financial.items.Customer;
import com.abc.accenture.financial.items.account.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service(value = "bankService")
public final class BankServiceImpl implements BankService {

    public static final String ACCOUNT_TITTLE = "account";
    private final CustomerService customerService;

    @Autowired
    public BankServiceImpl(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void deposit(final Bank bank, final String customerName, final String accountName, final double amount,
                        final LocalDate transactionDate) {
        Optional<Customer> existsCustomer = findExistsCustomer(bank, customerName);
        existsCustomer.ifPresent(customer ->
                this.customerService.deposit(customer, accountName, amount, transactionDate));
    }

    @Override
    public void withdraw(final Bank bank, final String customerName, final String accountName, final double amount,
                         final LocalDate transactionDate) {
        Optional<Customer> existsCustomer = findExistsCustomer(bank, customerName);
        existsCustomer.ifPresent(customer -> this.customerService.withdraw(customer, accountName, amount));
    }

    @Override
    public CustomerOperation openAccount(final Bank bank, final String name, final String accountName,
                                         final AccountType accountType) {
        Optional<Customer> existsCustomerOptional = findExistsCustomer(bank, name);
        if (existsCustomerOptional.isPresent()) {
            return customerService.openAccount(existsCustomerOptional.get(), accountName, accountType);
        } else {
            Customer newCustomer = new Customer(name);
            this.addCustomer(bank, newCustomer);
            return customerService.openAccount(newCustomer, accountName, accountType);
        }
    }

    @Override
    public void addCustomer(final Bank bank, final Customer customer) {
        bank.getCustomers().add(customer);
    }

    @Override
    public Customer getCustomer(Bank bank, String customerName) {
        return bank.getCustomers()
                .stream()
                .filter(customer -> customerName.equals(customer.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("This customer not exists: " + customerName));
    }

    @Override
    public String customerSummary(final Bank bank) {
        StringBuilder summary = new StringBuilder("Customer Summary");
        bank.getCustomers().forEach(customer ->
                summary.append("\n - ").append(customer.getName()).append(" (")
                        .append(format(customerService.getNumberOfAccounts(customer))).append(")"));

        return summary.toString();
    }

    @Override
    public double totalInterestPaid(final Bank bank) {
        return bank.getCustomers()
                .stream()
                .mapToDouble(customerService::totalInterestEarned)
                .sum();
    }

    private static Optional<Customer> findExistsCustomer(final Bank bank, final String name) {
        return bank.getCustomers().stream().filter(customer -> name.equals(customer.getName())).findFirst();
    }

    private String format(int number) {
        return number + " " + (number == 1 ? ACCOUNT_TITTLE : ACCOUNT_TITTLE + "s");
    }
}
