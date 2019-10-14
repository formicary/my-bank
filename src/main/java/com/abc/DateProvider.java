package com.abc;

import java.time.LocalDate;

public class DateProvider {

	private static DateProvider instance = null;

	private LocalDate currentDate;

	// Singleton implementation of a date provider for testing purposes.
	// For a real-time application, this would be bad practice and threading would
	// be a preferable solution using an executor or Timer alongside LocalTime.now()
	// to execute the daily tasks function.
	private DateProvider(LocalDate currentDate) {
		this.currentDate = currentDate;
	}

	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider(LocalDate.now());

		return instance;
	}

	// Fast forward the number of days for unit testing the accounts
	public void addDays(int days) {
		if (days > 0)
			currentDate = currentDate.plusDays(days);
	}

	// Fast-forward the number of days for the bank
	public void addDays(Bank bank, int days) {
		if (days > 0) {
			currentDate = currentDate.plusDays(days);

			dailyTasks(bank);
		}
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}

	// Complete the daily tasks that take place at the bank
	private void dailyTasks(Bank bank) {
		for (Account account : bank.getAccounts()) {
			account.dailyTasks();
		}
	}

}
