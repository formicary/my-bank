package com.abc;

import static java.lang.Math.abs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.abc.Account.Type;

/**
 * A class representing a bank. Contains a list of customers.
 * 
 * @author Filippos Zofakis
 */
public class Bank implements Runnable {
    private List<Customer> customers;
    
    // scheduler
    private final ScheduledExecutorService scheduler;


    /**
     * Constructor initialising a Bank object with an empty list of customers.
     */
    public Bank() {
        customers = new ArrayList<Customer>();
        
        scheduler = Executors.newScheduledThreadPool(1);

        // Calculates the time of the next instance of 00:00.
        Long nextMidnight = LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay(), ChronoUnit.MINUTES);
        
        // Schedules the task defined inside the run method to execute every day at midnight.
        scheduler.scheduleAtFixedRate(this, nextMidnight, TimeUnit.DAYS.toMinutes(1), TimeUnit.MINUTES);
    }

    /**
     * Overrides the run method of the Runnable Interface. Runs a task that
     * accrues interest to all customer accounts at the bank.
     */
    public void run() {
        double totalInterestPaidOut = 0;

        for (Customer customer : customers) {
            for (Account account : customer.getAllAccounts()) {
                totalInterestPaidOut += account.accrueInterestDaily(this);
            }
        }

        System.out
        .println("Total interest paid out across all accounts today: " + toDollars(totalInterestPaidOut) + ".");
    }

    /**
     * Adds a specified customer to the bank's list.
     * 
     * @param customer
     *            The Customer object to be added.
     */
    public void addCustomer(Customer customer) {
        if (customer != null)
            customers.add(customer);
    }

    /**
     * Returns a summary of all the the customers and their accounts at the
     * bank.
     * 
     * @return A String containing aggregate information about all accounts at
     *         the bank.
     */
    public String customerSummary() {
        String summary = "Customer Summary:";
        for (Customer customer : customers)
            summary += "\n - " + customer.getName() + " (" + format(customer.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Formats the number of accounts according to the correct plural form. If
     * the number passed in as an argument is one, returns the word unaltered,
     * otherwise adds an 's' at the end.
     * 
     * @param number
     *            The number of accounts of the customer.
     * @param word
     *            The word to be formatted.
     * @return A String formatted in singular or plural form, depending on
     *         number.
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Returns the total interest paid on all customer accounts at the bank.
     * 
     * @return A double representing the total interest paid on all accounts.
     */
    public double getTotalInterestPaid() {
        double totalAcrossAllAccounts = 0;

        for (Customer customer : customers)
            totalAcrossAllAccounts += customer.totalInterestEarned();

        return totalAcrossAllAccounts;
    }

    /**
     * Returns the customer of the bank according to customer number. For
     * example, for an input of 1 returns the first customer (at index position
     * 0). For an input of 2 returns the second customer, and so on.
     * 
     * @return A Customer object representing the customer at the given index.
     */
    public Customer getCustomer(int customerNumber) {
        // Converting to 0-index notation.
        customerNumber -= 1;

        // Performing the necessary validation.
        if (customerNumber < 0 || customerNumber >= customers.size())
            throw new IllegalArgumentException("Please enter a valid customer account number!");

        return customers.get(customerNumber);
    }

    /**
     * Returns the daily interest rate, depending on account type.
     * 
     * @param The account to which the interest rate should be applied.
     * @return A double representing the daily interest rate for the specified account.
     */
    public double getDailyInterestRate(Type accountType) {
        switch(accountType) {
        case CHECKING:
            return 0.001 / 365;
        case SAVINGS:
            return 0.002 / 365;
        case MAXI_SAVINGS:
            return 0.05 / 365;
        default:
            throw new IllegalArgumentException("Invalid account type!");
        }
    }

    /**
     * Returns a representation of the dollar amount of a transaction.
     * 
     * @param amount
     *            The amount to be represented as dollars.
     * @return A String representation of an amount formatted as US dollars.
     */
    public static String toDollars(double amount) {
        return String.format("$%,.2f", abs(amount));
    }
}
