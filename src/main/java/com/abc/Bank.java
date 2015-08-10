package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

public class Bank {
	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();

		/* a 24 hour interval specified in milliseconds */
		long interval = 1000 * 60 * 60 * 24;
		/*
		 * a Calendar object set for 16:00. This is passed to setTimer, so that
		 * the InterestCalculator is scheduled every afternoon at 4pm.
		 */
		Calendar scheduleTime = Calendar.getInstance();
		scheduleTime.set(Calendar.HOUR_OF_DAY, 16);
		scheduleTime.set(Calendar.MINUTE, 0);
		scheduleTime.set(Calendar.SECOND, 0);
		scheduleTime.set(Calendar.MILLISECOND, 0);
		setTimer(scheduleTime, interval);
	}

	/**
	 * accrueDailyInterest() iterates through all of the accounts of all of the
	 * bank's customers. For each, it calculates the daily interest rate as the
	 * interest rate per annum over 365, compounds the account's interest and
	 * adjusts the balance accordingly.
	 */
	public void accrueDailyInterest() {
		for (Customer c : customers) {
			System.out.println("Customer: " + c.getName());
			for (Account a : c.getAccounts()) {
				BigDecimal dailyInterestRate = a.getInterestRateForBalance()
						.divide(BigDecimal.valueOf(365.0), 12,
								RoundingMode.HALF_EVEN);

				/*
				 * balance += (interestRate/365) * balance, with trailing 0s
				 * removed.
				 */
				a.balance = a.balance
						.add(dailyInterestRate.multiply(a.balance))
						.stripTrailingZeros();

				System.out.println("Balance after compound interest for "
						+ a.getAccountNumber() + ": " + a.balance);
			}

		}
	}

	public void addCustomer(Customer customer) {
		for (Customer c : customers) {
			if (c.getID() == customer.getID()) {
				System.out.println("A customer with this ID already exists."
						+ "Please try again with a different identifier.");
				return;
			}
		}
		customers.add(customer);
	}

	public String customerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " ("
					+ format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	/**
	 * Make sure correct plural of word is created based on the number passed
	 * in. If number passed in is 1 just return the word; otherwise add an 's'
	 * at the end
	 */
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	public Customer getCustomer(int id) {
		for (Customer c : customers) {
			if (c.getID() == id) {
				return c;
			}
		}
		System.out.println("No customer found with that ID. Please try again.");
		return null;
	}

	/**
	 * Creates a timer object, which is used to schedule the daily compound
	 * interest calculations. Input is a Calendar object specifying the start of
	 * the timer, and a long specifying the interval between schedule
	 * executions.
	 */
	public void setTimer(Calendar time, long interval) {
		Timer timer = new Timer();
		/*
		 * test cases allowing the operation to be performed during Maven
		 * testing, rather than at a specific time each day.
		 * 
		 * Calendar testTime = Calendar.getInstance();
		 * testTime.setTime(DateProvider.getInstance().now());
		 * 
		 * timer.schedule(new InterestCalculator(this, testTime),
		 * testTime.getTime(), interval);
		 */

		// Scheduled to run every day at 4pm (see time)
		timer.schedule(new InterestCalculator(this, time), time.getTime(),
				interval);

	}

	/**
	 * Calculate the sum of interests paid to customers.
	 * 
	 * @return the total amount owed to the bank's customers.
	 */
	public BigDecimal totalInterestPaid() {
		BigDecimal total = BigDecimal.ZERO;
		for (Customer c : customers)
			total = total.add(c.totalInterestEarned());
		return total;
	}
}
