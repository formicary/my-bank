package com.abc;

import com.abc.Accounts.Account;
import com.abc.util.DateProvider;
import com.abc.util.Money;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bank {
    private List<Customer> customers;

    Timer interestTimer;

    public Bank() {
        customers = new ArrayList<>();

        // starts the timer to pay customers daily
        startPayingInterestDaily();
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

    public Money totalInterestPaid() {
        Money total = Money.ZERO;
        for(Customer c: customers)
            total = total.add(c.totalInterestEarned());
        return total;
    }

    /**
     * adds the daily interest to every account of each customer
     */
    public void payDailyInterest(){
        for (Customer c: customers) {
            payDailyInterestToCustomer(c);
        }
    }
    /**
     * adds the daily interest earned to all of the customers accounts
     * @param customer
     */
    private void payDailyInterestToCustomer(Customer customer){
        for (Account a : customer.getAccounts()) {
            a.deposit(a.dailyInterestEarned(), Transaction.BANK);
        }
    }

    /**
     * interest is paid to the customers at midnight UTC+0 every day
     */
    public final class InterestPayer extends TimerTask {
        @Override
        public void run() {
            Bank.this.payDailyInterest();
        }

        private final static long ONCE_PER_DAY = 1000 * 60 * 60 * 24; // milliseconds in a day
    }

    /**
     * initialises timer
     */
    private void startPayingInterestDaily(){
        TimerTask interestPayer = new InterestPayer();

        interestTimer = new Timer();
        interestTimer.scheduleAtFixedRate(interestPayer, getNextMidnight(), InterestPayer.ONCE_PER_DAY);
    }

    /**
     *
     * @return milliseconds until midnight
     */
    private long getNextMidnight() {
        Instant now = DateProvider.now();

        LocalDateTime currentTime = LocalDateTime.now(); // current date and time
        LocalDateTime midnight = currentTime.toLocalDate().atStartOfDay().plusDays(1); // midnight coming up
        Instant midnightInstant = midnight.toInstant(OffsetDateTime.now().getOffset());

        // time until midnight in milliseconds
        return midnightInstant.toEpochMilli() - now.toEpochMilli();
    }
}
